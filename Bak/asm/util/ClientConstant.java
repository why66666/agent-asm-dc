package com.weaver.agent.asm.util;


import java.util.HashMap;
import java.util.Map;

public class ClientConstant {
    /**
     * 服务端ip
     */
    private static final int SERVER_POST = 1;
    /**
     * 客户端ip
     */
    private static final int CLIENT_IP = 2;
    /**
     * session
     */
    private static final int CLIENT_SESSION_ID = 3;
    /**
     * 连接接口
     */
    private static final int SERVER_CONNECT_POST = 4;
    /**
     * 发送track
     */
    private static final int SERVER_TRACK_POST = 5;
    private static final int SERVER_FILE_DEL_POST = 6;
    private static final int TASK_STATUS_POST = 7;
    private static final int SERVER_TRACE_POST = 8;

    public static Map<Integer, String> CONS_INFO = new HashMap<Integer, String>();
    static {
        String serverPort = PropUtil.getPropValue("server.ip")+":"+PropUtil.getPropValue("server.port");
        String clientIp = ClientInit.getLocalIP();
        CONS_INFO.put(SERVER_POST,"http://"+serverPort+"/agent");
        CONS_INFO.put(CLIENT_IP,clientIp);
        CONS_INFO.put(SERVER_CONNECT_POST,"http://"+serverPort+"/agent/addEntity/"+clientIp);
        CONS_INFO.put(SERVER_TRACK_POST,"http://"+serverPort+"/agent/trackdata");
        CONS_INFO.put(SERVER_FILE_DEL_POST,"http://"+serverPort+"/agent/setFileDel");
        CONS_INFO.put(TASK_STATUS_POST,"http://"+serverPort+"/agent/updateStatus");
        CONS_INFO.put(SERVER_TRACE_POST,"http://"+serverPort+"/agent/traceData");
    }
}
