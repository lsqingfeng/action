package com.lsqingfeng.action.es.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsqingfeng.action.es.dao.CooActMapper;
import com.lsqingfeng.action.es.dao.EventMapper;
import com.lsqingfeng.action.es.pojo.Act;
import com.lsqingfeng.action.es.pojo.Event;
import com.lsqingfeng.action.es.pojo.entity.CooActDO;
import com.lsqingfeng.action.es.pojo.entity.EventDO;
import com.lsqingfeng.action.es.util.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: EventController
 * @description: 测试事件通过elaticSearch 查询
 * @author: sh.Liu
 * @create: 2020-05-27 09:59
 */
@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {

    public static final String indexName = "event";

    @Resource
    private EsUtil esUtil;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private CooActMapper cooActMapper;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private ModelMapper modelMapper = new ModelMapper();

    @RequestMapping("/init")
    public boolean init() throws Exception {
        boolean re = esUtil.isIndexExists(indexName);
        if(re){
            esUtil.delIndex(indexName);
        }
        boolean re1 = esUtil.createIndex(Event.class);
        List<Event> list = new ArrayList<>();
        List<EventDO> eventDOS = eventMapper.selectList(new LambdaQueryWrapper<>());
        if (eventDOS != null && eventDOS.size() > 0) {
            for (EventDO eventDO : eventDOS) {
                Event event = modelMapper.map(eventDO, Event.class);
                // 查询act
                CooActDO cooActDO = cooActMapper.selectOne(new LambdaQueryWrapper<CooActDO>().eq(CooActDO::getBizId, event.getEventId()));
                if (cooActDO != null) {
                    Act act = modelMapper.map(cooActDO, Act.class);
                    event.setAction(act);
                }
                list.add(event);
            }
        }

        boolean b = esUtil.saveBatch(indexName, list);
        return b;
    }


    @RequestMapping("/create")
    public boolean createIndex() throws Exception {
        boolean re = esUtil.isIndexExists(indexName);
        if(re){
            esUtil.delIndex(indexName);
        }

        boolean re1 = esUtil.createIndex(Event.class);
        return re1;
    }

    @RequestMapping("/update")
    public boolean upadteIndex() throws Exception {
        boolean re1 = esUtil.updateIndex(Event.class);
        return re1;
    }

    /**
     * 批量插入文档
     * @return
     */
    @RequestMapping("saveBatch")
    public boolean saveBatch() throws IOException {
        List<Event> list = new ArrayList<>();

        List<EventDO> eventDOS = eventMapper.selectList(new LambdaQueryWrapper<>());
        if (eventDOS != null && eventDOS.size() > 0) {
            for (EventDO eventDO : eventDOS) {
                Event event = modelMapper.map(eventDO, Event.class);
                // 查询act
                CooActDO cooActDO = cooActMapper.selectOne(new LambdaQueryWrapper<CooActDO>().eq(CooActDO::getBizId, event.getEventId()));
                if (cooActDO != null) {
                    Act act = modelMapper.map(cooActDO, Act.class);
                    event.setAction(act);
                }
                list.add(event);
            }
        }

        boolean b = esUtil.saveBatch(indexName, list);
        return b;
    }

    @RequestMapping("delDoc")
    public boolean deleteDoc(String docId) throws IOException {
        boolean b = esUtil.deleteDoc(indexName, docId);
        return b;
    }

    @RequestMapping("getDoc")
    public String getDoc(String docId) throws IOException {
        String re  = esUtil.queryById(indexName, docId);
        return re;
    }

    @RequestMapping("getByBizId")
    public Event getByBizId(String eventId) throws IOException {
        Event event =  esUtil.getByBizId(indexName, eventId);
        return event;
    }

    @RequestMapping("updateDoc")
    public boolean updateDoc() throws IOException {
        EventDO eventDO = eventMapper.selectById(1111111492);
        Event event = modelMapper.map(eventDO, Event.class);
        CooActDO cooActDO = cooActMapper.selectOne(new LambdaQueryWrapper<CooActDO>().eq(CooActDO::getBizId, event.getEventId()));
        if (cooActDO != null) {
            Act act = modelMapper.map(cooActDO, Act.class);
            event.setAction(act);
        }

        event.setAddress("黑龙江省哈尔滨市南岗区薛家镇苏屯");
        return  esUtil.updateDoc(indexName, "EaU-eHIBQqpxiwY5xMXg", event);
    }

    @RequestMapping("updateById")
    public boolean updateById() throws IOException {
        EventDO eventDO = eventMapper.selectById(1111111492);
        Event event = modelMapper.map(eventDO, Event.class);
        CooActDO cooActDO = cooActMapper.selectOne(new LambdaQueryWrapper<CooActDO>().eq(CooActDO::getBizId, event.getEventId()));
        if (cooActDO != null) {
            Act act = modelMapper.map(cooActDO, Act.class);
            event.setAction(act);
        }

        event.setAddress("黑龙江省哈尔滨市南岗区薛家镇苏屯");
        return  esUtil.updateDoc(indexName, "EaU-eHIBQqpxiwY5xMXg", event);
    }

    /**
     *  //返回和排除列
     *    if (!CollectionUtils.isEmpty(includeFields) || !CollectionUtils.isEmpty(excludeFields)) {
     *           sourceBuilder.fetchSource(includeFields, excludeFields);
     *     }
     * @param word
     * @return
     * @throws IOException
     */
    @RequestMapping("/search")
    public String search(String word) throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 关键字匹配所有字段
        if( !StringUtils.isEmpty(word)){
            sourceBuilder.query(QueryBuilders.queryStringQuery(word));
        }
        sourceBuilder.sort(new FieldSortBuilder("createTime").order(SortOrder.ASC));

        // 高亮显示：--- 搜索字段命中时才会高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("eventDesc");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("registerName");
        highlightBuilder.field(highlightUser);
        HighlightBuilder.Field areaRegionName = new HighlightBuilder.Field("areaRegionName");
        highlightBuilder.field(areaRegionName);

        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            // es 的ID
            String id = hit.getId();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        log.info("返回总数为：" + hits.getTotalHits());
        List<Event> list = JSON.parseArray(jsonArray.toJSONString(), Event.class);
        return jsonArray.toJSONString();
    }


    // 获取坐席员待办列表
    @RequestMapping("/daiban")
    public String daibanList() throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.termQuery("deleteFlag", 0));
        boolBuilder.mustNot(QueryBuilders.termQuery("eventStateId", 3));
        boolBuilder.mustNot(QueryBuilders.termQuery("actPropertyId", 102));
        // in 语句改写
        boolBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("action.partId","0"))
                .should(QueryBuilders.termQuery("action.partId","40")));
        boolBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("action.roleId","21")));
        boolBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("action.unitId","0"))
                .should(QueryBuilders.termQuery("action.unitId","12")));


        searchSourceBuilder.query(boolBuilder);
        searchSourceBuilder.sort(new FieldSortBuilder("createTime").order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        log.info("返回总数为：" + hits.getTotalHits());
        return jsonArray.toJSONString();
    }


    // 已登记事件列表  + 分页
    @RequestMapping("/dengji")
    public String dengji(@RequestParam int page, @RequestParam int pageSize) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 条件查询
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.termQuery("deleteFlag", 0));
        boolBuilder.must(QueryBuilders.termQuery("srcGroupCode", "hotLine"));
        boolBuilder.must(QueryBuilders.termQuery("registerId", 40));
        boolBuilder.mustNot(QueryBuilders.termQuery("actPropertyId", 0));

        searchSourceBuilder.query(boolBuilder);

        // 排序
        searchSourceBuilder.sort(new FieldSortBuilder("createTime").order(SortOrder.DESC));

        // 高亮显示：
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("eventDesc");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("registerName");
        highlightBuilder.field(highlightUser);
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);

        // 分页, 假设页数从0开始
        page = page <= -1 ? 0 : page;
        pageSize = pageSize >= 1000 ? 1000 : pageSize;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        searchSourceBuilder.from(page * pageSize);
        searchSourceBuilder.size(pageSize);


        // 查询
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();

        // 结果解析及封装
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        log.info("返回总数为：" + hits.getTotalHits());
        return jsonArray.toJSONString();
    }



}
