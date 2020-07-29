package com.lsqingfeng.action.es.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.lsqingfeng.action.es.dao.CooActMapper;
import com.lsqingfeng.action.es.dao.EventMapper;
import com.lsqingfeng.action.es.pojo.EsAct;
import com.lsqingfeng.action.es.pojo.EsEvent;
import com.lsqingfeng.action.es.pojo.entity.CooActDO;
import com.lsqingfeng.action.es.pojo.entity.EventDO;
import com.lsqingfeng.action.es.pojo.vo.ComprehensiveQueryEventVO;
import com.lsqingfeng.action.es.util.ElasticsearchUtil;
import com.lsqingfeng.action.es.util.ModelMapperUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: EsServiceImpl
 * @description:
 * @author: sh.Liu
 * @date: 2020-07-29 14:22
 */
@Slf4j
public class EsServiceImpl {
    @Value("${elasticsearch.keyword.fields}")
    private String queryFields;

    private final ElasticsearchUtil esUtil;

    private final CooActMapper actMapper;

    private final EventMapper eventMapper;

    public EsServiceImpl(ElasticsearchUtil esUtil, CooActMapper actMapper, EventMapper eventMapper) {
        this.esUtil = esUtil;
        this.actMapper = actMapper;
        this.eventMapper = eventMapper;
    }


    public PageInfo<ComprehensiveQueryEventVO> pageComprehensiveQuery(ComprehensiveQueryEventVO comprehensiveQueryEventVO) throws Exception {
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(comprehensiveQueryEventVO);
        PageInfo page = esUtil.search(searchSourceBuilder, comprehensiveQueryEventVO.getPageNum(), comprehensiveQueryEventVO.getPageSize(), EsEvent.class);
        // 转换
        List<ComprehensiveQueryEventVO> list = convertList(page.getList());
        page.setList(list);

        return page;
    }

    public List<ComprehensiveQueryEventVO> listComprehensiveQuery(ComprehensiveQueryEventVO comprehensiveQueryEventVO) throws Exception {
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(comprehensiveQueryEventVO);
        List<EsEvent>  list = esUtil.search(searchSourceBuilder, EsEvent.class);
        List<ComprehensiveQueryEventVO> resultList = convertList(list);
        return resultList;
    }

    // 将Es实体砖换成vo
    private List<ComprehensiveQueryEventVO> convertList(List<EsEvent> list){
        List<ComprehensiveQueryEventVO> resultList = new ArrayList<>();
        if (list != null) {
            resultList = list.stream().map(s -> {
                // 转换：
                ComprehensiveQueryEventVO vo = ModelMapperUtil.map(s, ComprehensiveQueryEventVO.class);
                vo.setPhone(s.getPublicReporterTel());
                // 转换act
                if (s.getAct() != null) {
                    vo.setDeadLine(s.getAct().getDeadLine());
                    vo.setActTimeStateId(s.getAct().getActTimeStateId());
                    vo.setProcDeadLine(s.getAct().getDeadLine());
                }
                // 转换visitor
                if (s.getVisitor() != null) {
                    vo.setVisitorTime(s.getVisitor().getVisitorTime());
                    vo.setResultLabel(s.getVisitor().getResultLabel());
                }
                return vo;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    public boolean saveOrUpdate(Integer eventId) throws Exception {

        // 1. 如果索引不存在则创建
        boolean re = esUtil.createIndexIfNotExist(EsEvent.class);
        // 如果返回true, 代表该索引不存在，已经创建了，那么此时应该做一次全量同步
        if (re) {
            batchSaveOrUpdate();
            return true;
        }

        // 2. 执行到此处，说明索引存在，那么做单条数据同步
        EventDO eventDO = eventMapper.selectById(eventId);
        EsEvent event = ModelMapperUtil.map(eventDO, EsEvent.class, "yyyy-MM-dd HH:mm:ss");
//        event.setEventStateName(EventStateEnum.getLabel(event.getEventStateId()));

        // 查询act
        CooActDO cooActDO = actMapper.selectOne(new LambdaQueryWrapper<CooActDO>().eq(CooActDO::getBizId, event.getEventId()));
        if (cooActDO != null) {
            EsAct act =  ModelMapperUtil.map(cooActDO, EsAct.class, "yyyy-MM-dd HH:mm:ss");
//            act.setActTimeStateName(ActTimeStateEnum.getLabel(act.getActTimeStateId()));
            event.setAct(act);
        }

//        // 查询visitor:
//        EventVisitorDO visitorDO = visitorService.getOne(new LambdaQueryWrapper<EventVisitorDO>().eq(EventVisitorDO::getEventId, eventDO.getEventId()));
//        if (visitorDO != null) {
//            EsVisitor visitor = ModelMapperUtil.map(visitorDO, EsVisitor.class, "yyyy-MM-dd HH:mm:ss");
//            visitor.setVisitorTime(DatesUtil.localDateTime2Str(visitorDO.getVisitorTime(), "yyyy-MM-dd HH:mm:ss"));
//            event.setVisitor(visitor);
//        }

        IndexResponse indexResponse = esUtil.index(event);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            log.info("eventId {}, 在es中对应的数据创建成功");
            return true;
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            log.info("eventId {}, 在es中对应的数据更新成功");
            return true;
        }
        return false;
    }

    /**
     * mysql数据库和es全量同步
     * @return
     * @throws Exception
     */
    public boolean batchSaveOrUpdate() throws Exception {
        // 1. 如果索引不存在则创建
        esUtil.createIndexIfNotExist(EsEvent.class);

        // 2. 全量同步
        List<EsEvent> esEventList = new ArrayList<>();
        // 查询所有es数据，不包含草稿箱  eventStateId != 0
        List<EventDO> list = eventMapper.selectList(
                new LambdaQueryWrapper<EventDO>().ne(EventDO::getEventStateId,0));
        if (list != null && list.size() > 0) {
            for (EventDO eventDO : list) {
                EsEvent event = ModelMapperUtil.map(eventDO, EsEvent.class, "yyyy-MM-dd HH:mm:ss");
//                event.setEventStateName(EventStateEnum.getLabel(event.getEventStateId()));
                // 查询act
                CooActDO cooActDO = actMapper.selectOne(new LambdaQueryWrapper<CooActDO>().eq(CooActDO::getBizId, event.getEventId()));
                if (cooActDO != null) {
                    EsAct act =  ModelMapperUtil.map(cooActDO, EsAct.class, "yyyy-MM-dd HH:mm:ss");
//                    act.setActTimeStateName(ActTimeStateEnum.getLabel(act.getActTimeStateId()));
                    event.setAct(act);
                }

//                // 查询visitor:
//                EventVisitorDO visitorDO = visitorService.getOne(new LambdaQueryWrapper<EventVisitorDO>().eq(EventVisitorDO::getEventId, eventDO.getEventId()));
//                if (visitorDO != null) {
//                    EsVisitor visitor = ModelMapperUtil.map(visitorDO, EsVisitor.class, "yyyy-MM-dd HH:mm:ss");
//                    visitor.setVisitorTime(DatesUtil.localDateTime2Str(visitorDO.getVisitorTime(), "yyyy-MM-dd HH:mm:ss"));
//                    event.setVisitor(visitor);
//                }
                esEventList.add(event);
            }
        }

        boolean b = esUtil.batchSaveOrUpdate(esEventList);
        return b;
    }


    /************************私有工具方法************************/

    /**
     * 拼接综合查询 查询条件
     * @param comprehensiveQueryEventVO
     * @return
     */
    private SearchSourceBuilder getSearchSourceBuilder(ComprehensiveQueryEventVO comprehensiveQueryEventVO){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (comprehensiveQueryEventVO.getPageNum() == null) {
            comprehensiveQueryEventVO.setPageNum(1);
        }

        if (comprehensiveQueryEventVO.getPageSize() == null) {
            comprehensiveQueryEventVO.setPageSize(10000);
        }

        sourceBuilder.from((comprehensiveQueryEventVO.getPageNum()-1)*comprehensiveQueryEventVO.getPageSize());
        sourceBuilder.size(comprehensiveQueryEventVO.getPageSize());

        // 上报时间拼接时/分/秒
        if (!ObjectUtils.isEmpty(comprehensiveQueryEventVO.getReportStartTime())) {
            comprehensiveQueryEventVO.setReportStartTime(comprehensiveQueryEventVO.getReportStartTime() + " 00:00:00");
        }
        if (!ObjectUtils.isEmpty(comprehensiveQueryEventVO.getReportEndTime())) {
            comprehensiveQueryEventVO.setReportEndTime(comprehensiveQueryEventVO.getReportEndTime() + " 23:59:59");
        }
        // 截止时间拼接时/分/秒
        if (!ObjectUtils.isEmpty(comprehensiveQueryEventVO.getDeadLineStartTime())) {
            comprehensiveQueryEventVO.setDeadLineStartTime(comprehensiveQueryEventVO.getDeadLineStartTime() + " 00:00:00");
        }
        if (!ObjectUtils.isEmpty(comprehensiveQueryEventVO.getDeadLineEndTime())) {
            comprehensiveQueryEventVO.setDeadLineEndTime(comprehensiveQueryEventVO.getDeadLineEndTime() + " 23:59:59");
        }

        // 符合条件查询
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        // event_state_id != 0 and event_state_id != 11 and delete_flag != 1
        boolBuilder.mustNot(QueryBuilders.termQuery("eventStateId", 0));
        boolBuilder.mustNot(QueryBuilders.termQuery("eventStateId", 11));
        boolBuilder.mustNot(QueryBuilders.termQuery("delateFlag", 1));

        // 动态条件----keyword
        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getKeyword())) {
            // 如果配置了查询字段，按照指定字段做查询
            if (StringUtils.isNotEmpty(queryFields)) {
                String[] queryFieldArr = queryFields.split(",");

                boolBuilder.must(QueryBuilders.boolQuery()
                        .should(QueryBuilders.wildcardQuery("eventCode","*"+comprehensiveQueryEventVO.getKeyword()+"*"))
                        .should(QueryBuilders.multiMatchQuery(comprehensiveQueryEventVO.getKeyword(), queryFieldArr)));
            }else{
                // 否则所有字段做检索
                boolBuilder.must(QueryBuilders.queryStringQuery(comprehensiveQueryEventVO.getKeyword()));
            }
        }

        // 拼接动态查询条件
        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventCode())) {
            boolBuilder.must(QueryBuilders.termQuery("eventCode", comprehensiveQueryEventVO.getEventCode()));
        }

        if (comprehensiveQueryEventVO.getEventGradeId() != null) {
            boolBuilder.must(QueryBuilders.termQuery("eventGradeId", comprehensiveQueryEventVO.getEventGradeId()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventGradeName())) {
            boolBuilder.must(QueryBuilders.termQuery("eventGradeNam", comprehensiveQueryEventVO.getEventGradeName()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventTypeName())) {
            boolBuilder.must(QueryBuilders.termQuery("eventTypeName", comprehensiveQueryEventVO.getEventTypeName()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventTypeCode())) {
            boolBuilder.must(QueryBuilders.termQuery("eventTypeCode", comprehensiveQueryEventVO.getEventTypeCode()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventSubtypeName())) {
            boolBuilder.must(QueryBuilders.termQuery("eventSubtypeName", comprehensiveQueryEventVO.getEventSubtypeName()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventSubtypeCode())) {
            boolBuilder.must(QueryBuilders.termQuery("eventSubtypeCode", comprehensiveQueryEventVO.getEventSubtypeCode()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getAreaRegionName())) {
            boolBuilder.must(QueryBuilders.termQuery("areaRegionName", comprehensiveQueryEventVO.getAreaRegionName()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getAreaRegionCode())) {
            boolBuilder.must(QueryBuilders.termQuery("areaRegionCode", comprehensiveQueryEventVO.getAreaRegionCode()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getCommRegionName())) {
            boolBuilder.must(QueryBuilders.termQuery("commRegionName", comprehensiveQueryEventVO.getCommRegionName()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getCommRegionCode())) {
            boolBuilder.must(QueryBuilders.termQuery("commRegionCode", comprehensiveQueryEventVO.getCommRegionCode()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getStreetRegionName())) {
            boolBuilder.must(QueryBuilders.termQuery("streetRegionName", comprehensiveQueryEventVO.getStreetRegionName()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getStreetRegionCode())) {
            boolBuilder.must(QueryBuilders.termQuery("streetRegionCode", comprehensiveQueryEventVO.getStreetRegionName()));
        }

        if (comprehensiveQueryEventVO.getEventSrcId() != null) {
            boolBuilder.must(QueryBuilders.termQuery("eventSrcId", comprehensiveQueryEventVO.getEventSrcId()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getEventSrcName())) {
            boolBuilder.must(QueryBuilders.termQuery("eventSrcName", comprehensiveQueryEventVO.getEventSrcName()));
        }

        if (comprehensiveQueryEventVO.getProcTimeStateId() != null) {
            boolBuilder.must(QueryBuilders.termQuery("procTimeStateId", comprehensiveQueryEventVO.getProcTimeStateId()));
        }

        if (comprehensiveQueryEventVO.getPressFlag() != null) {
            boolBuilder.must(QueryBuilders.termQuery("pressFlag", comprehensiveQueryEventVO.getPressFlag()));
        }

        if (comprehensiveQueryEventVO.getOverseerFlag() != null) {
            boolBuilder.must(QueryBuilders.termQuery("overseerFlag", comprehensiveQueryEventVO.getOverseerFlag()));
        }

        if (comprehensiveQueryEventVO.getEventStateId() != null) {
            boolBuilder.must(QueryBuilders.termQuery("eventStateId", comprehensiveQueryEventVO.getEventStateId()));
        }

        if (comprehensiveQueryEventVO.getActTimeStateId() != null) {
            boolBuilder.must(QueryBuilders.termQuery("act.actTimeStateId", comprehensiveQueryEventVO.getActTimeStateId()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getReportStartTime())) {
            boolBuilder.must(QueryBuilders.rangeQuery("procStartTime").gte(comprehensiveQueryEventVO.getReportStartTime()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getReportEndTime())) {
            boolBuilder.must(QueryBuilders.rangeQuery("procStartTime").lte(comprehensiveQueryEventVO.getReportEndTime()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getDeadLineStartTime())) {
            boolBuilder.must(QueryBuilders.rangeQuery("act.deadLine").gte(comprehensiveQueryEventVO.getDeadLineStartTime()));
        }

        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getDeadLineEndTime())) {
            boolBuilder.must(QueryBuilders.rangeQuery("act.deadLine").lte(comprehensiveQueryEventVO.getDeadLineEndTime()));
        }
        sourceBuilder.query(boolBuilder);

        // 排序：
        if (StringUtils.isNotEmpty(comprehensiveQueryEventVO.getOrderField()) && StringUtils.isNotEmpty(comprehensiveQueryEventVO.getOrderSort())) {
            String esOrderField = null;
            if (QueryFieldEnum.PROC_START_TIME.getFieldName().equals(comprehensiveQueryEventVO.getOrderField())) {
                esOrderField = "procStartTime";
            } else if (QueryFieldEnum.PROC_DEAD_LINE.getFieldName().equals(comprehensiveQueryEventVO.getOrderField())) {
                esOrderField = "act.deadLine";
            } else if (QueryFieldEnum.PROC_END_TIME.getFieldName().equals(comprehensiveQueryEventVO.getOrderField())) {
                esOrderField = "procEndTime";
            } else if (QueryFieldEnum.VISITOR_TIME.getFieldName().equals(comprehensiveQueryEventVO.getOrderField())) {
                esOrderField = "visitor.visitorTime";
            }
            FieldSortBuilder fieldSortBuilder = new FieldSortBuilder(esOrderField);
            fieldSortBuilder = fieldSortBuilder.order("orderDesc".equals(comprehensiveQueryEventVO.getOrderSort()) ? SortOrder.DESC : SortOrder.ASC);
            sourceBuilder.sort(fieldSortBuilder);
        }

        return sourceBuilder;
    }

    /**
     * 综合查询动态排序字段枚举
     */
    public enum QueryFieldEnum {

        /**
         * 上报时间
         */
        PROC_START_TIME("上报时间", "proc_start_time", "procStartTime"),

        /**
         * 截止时间
         */
        PROC_DEAD_LINE("截止时间", "dead_line", "procDeadLine"),

        /**
         * 办结时间
         */
        PROC_END_TIME("办结时间", "proc_end_time", "procEndTime"),

        /**
         * 回访时间
         */
        VISITOR_TIME("回访时间", "visitor_time", "visitorTime");

        QueryFieldEnum(String desc, String columnName, String fieldName) {
            this.desc = desc;
            this.columnName = columnName;
            this.fieldName = fieldName;
        }

        private String desc;
        private String columnName;
        private String fieldName;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public static String getColumnName(String fieldName) {
            if (StringUtils.isBlank(fieldName)) {
                return "";
            }
            String columnName = "";
            QueryFieldEnum[] values = QueryFieldEnum.values();
            for (QueryFieldEnum queryFieldEnum : values) {
                if (queryFieldEnum.getFieldName().equals(fieldName)) {
                    columnName = queryFieldEnum.getColumnName();
                    break;
                }
            }
            return columnName;
        }
    }

}
