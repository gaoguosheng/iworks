package com.ggs.admin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tspms26 on 2017/3/16.
 */
public class JsonUtil {
    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String mapToJson(Map map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }


    public static Map jsonToMap(String json) throws IOException {
        return (Map) objectMapper.readValue(json, Map.class);

    }

    /**
     * 将对象序列化
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj).replace("null","\"\"");
    }

    /**
     * 反序列化对象字符串
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 反序列化字符串成为对象
     *
     * @param json
     * @param valueTypeRef
     * @return
     */
    public static <T> T jsonToBean(String json, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(json, valueTypeRef);

    }

    /**
     * jsonArray转化成list
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<?> clazz) throws Exception {
        List<T> list = new ArrayList<T>();
        // 指定容器结构和类型（这里是ArrayList和clazz）
        TypeFactory t = TypeFactory.defaultInstance();
        list = objectMapper.readValue(jsonStr,
                t.constructCollectionType(ArrayList.class, clazz));
        return list;
    }
}
