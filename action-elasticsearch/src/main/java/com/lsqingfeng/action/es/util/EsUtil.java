package com.lsqingfeng.action.es.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lsqingfeng.action.es.annotation.Document;
import com.lsqingfeng.action.es.enums.FieldType;
import com.lsqingfeng.action.es.pojo.Event;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @className: EsUtil
 * @description: es 操作工具类
 * @author: sh.Liu
 * @create: 2020-05-25 09:41
 */
@Component
@Slf4j
public class EsUtil {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    /**
     * 创建索引(默认分片数为5和副本数为1)
     * @param indexName
     * @throws IOException
     */
    public boolean createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                // 设置分片数为3， 副本为2
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.mapping(generateBuilder());
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
    public boolean createIndex(Class clazz) throws Exception {
        Document declaredAnnotation = (Document )clazz.getDeclaredAnnotation(Document.class);
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
     * 查看索引是否存在
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
     * @return
     */
    public IndexResponse add(String indexName, Object o) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
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

    public String search(String indexName, String word) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 关键字匹配所有字段
        sourceBuilder.query(QueryBuilders.queryStringQuery(word));

        // 前缀匹配
        sourceBuilder.query(QueryBuilders.prefixQuery("name", "张"));

        //范围查询： >=10   <= 20
        sourceBuilder.query(QueryBuilders.rangeQuery("age").gte(10).lte(20));


        searchRequest.source(sourceBuilder);
        sourceBuilder.sort(new FieldSortBuilder("age").order(SortOrder.ASC));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 批量插入文档
     * @param indexName
     * @param list
     * @return
     */
    public boolean saveBatch(String indexName, List list) throws IOException {
        BulkRequest request = new BulkRequest(indexName);
        for (Object o : list) {
            String jsonStr = JSON.toJSONString(o);
            request.add(new IndexRequest().source(jsonStr, XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

        for(BulkItemResponse bulkItemResponse : bulkResponse){
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            IndexResponse indexResponse = (IndexResponse) itemResponse;
            if(bulkItemResponse.isFailed()){
                return false;
            }
        }
        return true;
    }

    private XContentBuilder generateBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                // es7及以后去掉了映射类型--person
                builder.startObject("name");
                    {
                        builder.field("type", "text");
                        builder.field("analyzer", "ik_smart");
                    }
                    builder.endObject();
            }
            {
                builder.startObject("age");
                {
                    builder.field("type", "integer");
                }
                builder.endObject();
            }
            {
                builder.startObject("desc");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_smart");
                }
                builder.endObject();
            }
            {
                builder.startObject("id");
                {
                    builder.field("type", "integer");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        /*.startObject().field("properties")
            .startObject().field("person")
                .startObject("name")
                    .field("type" , "text")
                    .field("analyzer", "ik_smart")
                .endObject()
                .startObject("age")
                    .field("type" , "int")
                .endObject()
                .startObject("desc")
                    .field("type", "text")
                    .field("analyzer", "ik_smart")
                .endObject()
            .endObject()
        .endObject();*/
        return builder;
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
                com.lsqingfeng.action.es.annotation.Field declaredAnnotation = f.getDeclaredAnnotation(com.lsqingfeng.action.es.annotation.Field.class);

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
                    builder.endObject();
                }
            }
        }
        // 对应property
        builder.endObject();
        builder.endObject();
        return builder;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Class clazz = Event.class;

        Document doc = (Document) clazz.getAnnotation(Document.class);
        System.out.println(doc.index());
        System.out.println(doc.type());

        Field eventCode = clazz.getDeclaredField("eventCode");
        System.out.println(eventCode.isAnnotationPresent(com.lsqingfeng.action.es.annotation.Field.class));

        Field eventId = clazz.getDeclaredField("eventId");
        System.out.println(eventId.isAnnotationPresent(com.lsqingfeng.action.es.annotation.Field.class));

    }


}
