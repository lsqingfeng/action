package com.lsqingfeng.action.es.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lsqingfeng.action.es.annotation.Document;
import com.lsqingfeng.action.es.annotation.EsId;
import com.lsqingfeng.action.es.enums.FieldType;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @className: EsUtil
 * @description: es 操作工具类;
 *      这里均采用同步调用的方式
 * @author: sh.Liu
 * @create: 2020-05-25 09:41
 */
@Component
@Slf4j
public class ElasticsearchUtil {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引(默认分片数为5和副本数为1)
     * @param clazz 根据实体自动映射es索引
     * @throws IOException
     */
    public boolean createIndex(Class clazz) throws Exception {
        Document declaredAnnotation = (Document)clazz.getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
        }
        String indexName = declaredAnnotation.index();
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                // 设置分片数为3， 副本为2
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.mapping(generateBuilder(clazz));
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            log.info("创建索引成功！索引名称为{}", indexName);
            return true;
        }
        return false;
    }

    /**
     * 创建索引(默认分片数为5和副本数为1)
     * @param clazz 根据实体自动映射es索引
     * @throws IOException
     */
    public boolean createIndexIfNotExist(Class clazz) throws Exception {
        Document declaredAnnotation = (Document)clazz.getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
        }
        String indexName = declaredAnnotation.index();

        boolean indexExists = isIndexExists(indexName);
        if (!indexExists) {
            // 索引不存在
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            request.settings(Settings.builder()
                    // 设置分片数为3， 副本为2
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 2)
            );
            request.mapping(generateBuilder(clazz));
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            // 指示是否所有节点都已确认请求
            boolean acknowledged = response.isAcknowledged();
            // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            if (acknowledged || shardsAcknowledged) {
                log.info("创建索引成功！索引名称为{}", indexName);
                return true;
            }
        }
        return false;
    }

    /**
     * 更新索引(默认分片数为5和副本数为1)：
     * 只能给索引上添加一些不存在的字段
     * 已经存在的映射不能改
     *
     * @param clazz 根据实体自动映射es索引
     * @throws IOException
     */
    public boolean updateIndex(Class clazz) throws Exception {
        Document declaredAnnotation = (Document )clazz.getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
        }
        String indexName = declaredAnnotation.index();
        PutMappingRequest request = new PutMappingRequest(indexName);

        request.source(generateBuilder(clazz));
        AcknowledgedResponse response = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();

        if (acknowledged ) {
            log.info("更新索引索引成功！索引名称为{}", indexName);
            return true;
        }
        return false;
    }
    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public boolean delIndex(String indexName){
        boolean acknowledged = false;
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    public boolean isIndexExists(String indexName){
        boolean exists = false;
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            getIndexRequest.humanReadable(true);
            exists = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }


    /**
     * 添加单条数据
     * 提供多种方式：
     *  1. json
     *  2. map
     *      Map<String, Object> jsonMap = new HashMap<>();
     *      jsonMap.put("user", "kimchy");
     *      jsonMap.put("postDate", new Date());
     *      jsonMap.put("message", "trying out Elasticsearch");
     *      IndexRequest indexRequest = new IndexRequest("posts")
     *          .id("1").source(jsonMap);
     *  3. builder
     *      XContentBuilder builder = XContentFactory.jsonBuilder();
     *      builder.startObject();
     *      {
     *          builder.field("user", "kimchy");
     *          builder.timeField("postDate", new Date());
     *          builder.field("message", "trying out Elasticsearch");
     *      }
     *      builder.endObject();
     *      IndexRequest indexRequest = new IndexRequest("posts")
     *      .id("1").source(builder);
     * 4. source:
     *      IndexRequest indexRequest = new IndexRequest("posts")
     *     .id("1")
     *     .source("user", "kimchy",
     *         "postDate", new Date(),
     *         "message", "trying out Elasticsearch");
     *
     *   报错：  Validation Failed: 1: type is missing;
     *      加入两个jar包解决
     *
     *   提供新增或修改的功能
     *
     * @return
     */
    public IndexResponse index(Object o) throws Exception {
        Document declaredAnnotation = (Document )o.getClass().getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", o.getClass().getName()));
        }
        String indexName = declaredAnnotation.index();

        IndexRequest request = new IndexRequest(indexName);
        Field fieldByAnnotation = getFieldByAnnotation(o, EsId.class);
        if (fieldByAnnotation != null) {
            fieldByAnnotation.setAccessible(true);
            try {
                Object id = fieldByAnnotation.get(o);
                request =request.id(id.toString());
            } catch (IllegalAccessException e) {
                log.error("获取id字段出错：{}", e);
            }
        }

        String userJson = JSON.toJSONString(o);
        request.source(userJson, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return indexResponse;
    }


    /**
     * 根据id查询
     * @return
     */
    public String queryById(String indexName, String id) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, id);
        // getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String jsonStr = getResponse.getSourceAsString();
        return jsonStr;
    }

    /**
     * 查询封装返回json字符串
     * @param indexName
     * @param searchSourceBuilder
     * @return
     * @throws IOException
     */
    public String search(String indexName, SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
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

    /**
     * 查询封装，带分页
     * @param searchSourceBuilder
     * @param pageNum
     * @param pageSize
     * @param s
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> PageInfo<T> search(SearchSourceBuilder searchSourceBuilder, int pageNum, int pageSize, Class<T> s) throws Exception {
        Document declaredAnnotation = (Document )s.getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", s.getName()));
        }
        String indexName = declaredAnnotation.index();
        SearchRequest searchRequest = new SearchRequest(indexName);
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
        int total = (int)hits.getTotalHits().value;

        // 封装分页
        List<T> list = jsonArray.toJavaList(s);
        PageInfo<T> page = new PageInfo<>();
        page.setList(list);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(total);
        page.setPages(total== 0 ? 0: (total%pageSize == 0 ? total / pageSize : (total / pageSize) + 1));
        page.setHasNextPage(page.getPageNum() < page.getPages());
        return page;
    }

    /**
     * 查询封装，返回集合
     * @param searchSourceBuilder
     * @param s
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> search(SearchSourceBuilder searchSourceBuilder, Class<T> s) throws Exception {
        Document declaredAnnotation = (Document )s.getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", s.getName()));
        }
        String indexName = declaredAnnotation.index();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        // 封装分页
        List<T> list = jsonArray.toJavaList(s);
        return list;
    }


    /**
     * 批量插入文档
     * 文档存在 则插入
     * 文档不存在 则更新
     * @param list
     * @return
     */
    public <T> boolean batchSaveOrUpdate(List<T> list) throws Exception {
        Object o1 = list.get(0);
        Document declaredAnnotation = (Document )o1.getClass().getDeclaredAnnotation(Document.class);
        if(declaredAnnotation == null){
            throw new Exception(String.format("class name: %s can not find Annotation [@Document], please check", o1.getClass().getName()));
        }
        String indexName = declaredAnnotation.index();

        BulkRequest request = new BulkRequest(indexName);
        for (Object o : list) {
            String jsonStr = JSON.toJSONString(o);
            IndexRequest indexReq = new IndexRequest().source(jsonStr, XContentType.JSON);

            Field fieldByAnnotation = getFieldByAnnotation(o, EsId.class);
            if (fieldByAnnotation != null) {
                fieldByAnnotation.setAccessible(true);
                try {
                    Object id = fieldByAnnotation.get(o);
                    indexReq = indexReq.id(id.toString());
                } catch (IllegalAccessException e) {
                    log.error("获取id字段出错：{}", e);
                }
            }
            request.add(indexReq);
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

        for(BulkItemResponse bulkItemResponse : bulkResponse){
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            IndexResponse indexResponse = (IndexResponse) itemResponse;
            log.info("单条返回结果：{}", indexResponse);
            if(bulkItemResponse.isFailed()){
                log.error("es 返回错误{}",bulkItemResponse.getFailureMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * 删除文档
     * @param indexName： 索引名称
     * @param docId：     文档id
     */
    public boolean deleteDoc(String indexName, String docId) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName, docId);
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        // 解析response
        String index = deleteResponse.getIndex();
        String id = deleteResponse.getId();
        long version = deleteResponse.getVersion();
        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                log.info("删除失败，原因为 {}", reason);
            }
        }
        return true;
    }

    /**
     * 根据json类型更新文档
     * @param indexName
     * @param docId
     * @param o
     * @return
     * @throws IOException
     */
    public boolean updateDoc(String indexName, String docId, Object o) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, docId);
        request.doc(JSON.toJSONString(o), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        String index = updateResponse.getIndex();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {

        }
        return false;
    }

    /**
     * 根据Map类型更新文档
     * @param indexName
     * @param docId
     * @param map
     * @return
     * @throws IOException
     */
    public boolean updateDoc(String indexName, String docId, Map<String, Object> map) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, docId);
        request.doc(map);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        String index = updateResponse.getIndex();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {

        }
        return false;
    }


    public XContentBuilder generateBuilder(Class clazz) throws IOException {
        // 获取索引名称及类型
        Document doc = (Document) clazz.getAnnotation(Document.class);
        System.out.println(doc.index());
        System.out.println(doc.type());

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.startObject("properties");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(com.lsqingfeng.action.es.annotation.Field.class)) {
                // 获取注解
                com.lsqingfeng.action.es.annotation.Field  declaredAnnotation = f.getDeclaredAnnotation(com.lsqingfeng.action.es.annotation.Field.class);

                // 如果嵌套对象：
                /**
                 * {
                 *   "mappings": {
                 *     "properties": {
                 *       "region": {
                 *         "type": "keyword"
                 *       },
                 *       "manager": {
                 *         "properties": {
                 *           "age":  { "type": "integer" },
                 *           "name": {
                 *             "properties": {
                 *               "first": { "type": "text" },
                 *               "last":  { "type": "text" }
                 *             }
                 *           }
                 *         }
                 *       }
                 *     }
                 *   }
                 * }
                 */
                if (declaredAnnotation.type() == FieldType.OBJECT) {
                    // 获取当前类的对象-- Action
                    Class<?> type = f.getType();
                    Field[] df2 = type.getDeclaredFields();
                    builder.startObject(f.getName());
                    builder.startObject("properties");
                    // 遍历该对象中的所有属性
                    for (Field f2 : df2) {
                        if (f2.isAnnotationPresent(com.lsqingfeng.action.es.annotation.Field.class)) {
                            // 获取注解
                            com.lsqingfeng.action.es.annotation.Field declaredAnnotation2 = f2.getDeclaredAnnotation(com.lsqingfeng.action.es.annotation.Field.class);
                            builder.startObject(f2.getName());
                            builder.field("type", declaredAnnotation2.type().getType());
                            // keyword不需要分词
                            if (declaredAnnotation2.type() == FieldType.TEXT) {
                                builder.field("analyzer", declaredAnnotation2.analyzer().getType());
                            }
                            if (declaredAnnotation2.type() == FieldType.DATE) {
                                builder.field("format", "yyyy-MM-dd HH:mm:ss");
                            }
                            builder.endObject();
                        }
                    }
                    builder.endObject();
                    builder.endObject();

                }else{
                    builder.startObject(f.getName());
                    builder.field("type", declaredAnnotation.type().getType());
                    // keyword不需要分词
                    if (declaredAnnotation.type() == FieldType.TEXT) {
                        builder.field("analyzer", declaredAnnotation.analyzer().getType());
                    }
                    if (declaredAnnotation.type() == FieldType.DATE) {
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();
                }
            }
        }
        // 对应property
        builder.endObject();
        builder.endObject();
        return builder;
    }


   public static Field getFieldByAnnotation(Object o ,Class annotationClass){
       Field[] declaredFields = o.getClass().getDeclaredFields();
       if (declaredFields != null && declaredFields.length >0) {
           for(Field f : declaredFields){
                if (f.isAnnotationPresent(annotationClass)) {
                    return f;
                }
           }
       }
       return null;
   }

}
