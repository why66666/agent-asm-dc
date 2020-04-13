package com.weaver.agent.asm.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientInit {
    public static void initConnect() {
        connect();
    }

    static String getLocalIP() {
        String localIP = "";
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取本机IP
        localIP = addr != null ? addr.getHostAddress() : null;
        return localIP;
    }

    private static void connect() {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        StringBuilder resultBuilder = new StringBuilder();
        try {
            URL http = new URL(ClientConstant.CONS_INFO.get(4));
            conn = (HttpURLConnection) http.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuilder.append(temp);
            }
            System.out.println("=======Connect=======" + conn.getHeaderField("Set-Cookie"));
            Map<String, List<String>> map = conn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }
            String cookieVal = conn.getHeaderField("Set-Cookie");
            if (cookieVal != null) {
                ClientConstant.CONS_INFO.put(3,cookieVal.substring(0, cookieVal.indexOf(";")));
            }
            System.out.println(resultBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
