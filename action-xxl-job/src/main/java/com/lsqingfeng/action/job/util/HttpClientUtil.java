package com.lsqingfeng.action.job.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className: HttpClientUtil
 * @description:
 * @author: sh.Liu
 * @date: 2020-08-31 15:55
 */
public class HttpClientUtil {
    /**
     * get请求，参数拼接在地址上
     *
     * @param url 请求地址加参数
     * @return 响应
     */
    public static String get(String url) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get请求，参数放在map里
     *
     * @param url 请求地址
     * @param map 参数map
     * @return 响应
     */
    public static String getMap(String url, Map<String, String> map) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters(pairs);
            HttpGet get = new HttpGet(builder.build());
            response = httpClient.execute(get);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 发送post请求，参数用map接收
     *
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
    public static String postMap(String url, Map<String, Object> map) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), JSONObject.toJSONString(entry.getValue())));
        }
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * post请求，参数为json字符串
     *
     * @param url        请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    public static String postJson(String url, String jsonString) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonString.getBytes(StandardCharsets.UTF_8)));
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8);
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }

    /**
     * post请求（用于请求json格式的参数）
     * @param url 请求的url
     * @param params 请求的参数
     * @return 请求结果
     */
    public static String doPost(String url, Map<String,Object> params) throws IOException {

        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");
        StringEntity entityData = new StringEntity(JSONObject.toJSONString(params), StandardCharsets.UTF_8);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(entityData);
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
