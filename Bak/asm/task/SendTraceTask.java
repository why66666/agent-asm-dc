package com.weaver.agent.asm.task;

import com.weaver.agent.asm.entity.IndexHashMap;
import com.weaver.agent.asm.util.ClientConstant;
import com.weaver.agent.asm.util.HttpUtil;
import com.weaver.agent.asm.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author w
 * @date 2020-03-14 17:34
 */
public class SendTraceTask implements Runnable {
    private String ip;
    private IndexHashMap trace;
    private IndexHashMap data;
    private String url;
    private HashMap sqls;

    public SendTraceTask(String ip, IndexHashMap trace, IndexHashMap data, String url, HashMap<Integer, Set<String>> sqls) {
        this.ip = ip;
        this.trace = trace;
        this.data = data;
        this.url = url;
        this.sqls = sqls;
    }

    @Override
    public void run() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("ip", ip);
        map.put("trace", JsonUtil.toJson(trace));
        map.put("data", JsonUtil.toJson(data));
        map.put("url", url);
        map.put("sqls", JsonUtil.toJson(sqls));
        HttpUtil.sendPostOnJson(ClientConstant.CONS_INFO.get(8),JsonUtil.toJson(map));
        System.out.println("=======TrackOver!!!======");
    }
}
