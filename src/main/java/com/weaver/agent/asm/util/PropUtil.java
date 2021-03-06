package com.weaver.agent.asm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author w
 * @date 2020-03-10 13:39
 */
public class PropUtil {
    private static Map<String, Object> PROP_INFO = new HashMap<String, Object>();
    private static final String propPath = "/dc.properties";

    public static void initProp(){
        savePropValue("server.ip");
        savePropValue("server.port");
        saveListValue("element.allow");
        saveListValue("element.ban");
        saveListValue("data.ban");
    }

    private static void savePropValue(String key){
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream(propPath);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PROP_INFO.put(key,properties.get(key).toString());
    }
    private static void saveListValue(String key){
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream(propPath);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PROP_INFO.put(key,Arrays.asList(properties.get(key).toString().split(",")));
    }

    public static String getPropValue(String s) {
        return PROP_INFO.get(s).toString();
    }

    public static List<String> getListValue(String s) {
        return (List<String>)PROP_INFO.get(s);
    }
}
