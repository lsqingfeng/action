package com.lsqingfeng.action.job.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @className: JsonUtil
 * @description:
 * @author: sh.Liu
 * @date: 2020-08-31 15:56
 */
public class JsonUtil {
    /**
     * 对象转JSON字符串
     *
     * @param obj
     * @return
     */
    public static String object2Json(Object obj) {
        String result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map object2Map(Object obj) {
        String object2Json = object2Json(obj);
        Map<?, ?> result = jsonToMap(object2Json);
        return result;
    }

    /**
     * JSON字符串转Map对象
     *
     * @param json
     * @return
     */
    public static Map<?, ?> jsonToMap(String json) {
        return json2Object(json, Map.class);
    }

    /**
     * JSON转Object对象
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String json, Class<T> cls) {
        T result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T conveterObject(Object srcObject, Class<T> destObjectType) {
        String jsonContent = object2Json(srcObject);
        return json2Object(jsonContent, destObjectType);
    }

    public static Map<String, Object> formatHumpName(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = toFormatCol(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    public static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 获取map中第一个非空数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstNotNull(Map<K, V> map) {
        V obj = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
}
