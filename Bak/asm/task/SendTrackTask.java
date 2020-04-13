package com.weaver.agent.asm.task;



import com.weaver.agent.asm.entity.IndexHashMap;
import com.weaver.agent.asm.util.ClientConstant;
import com.weaver.agent.asm.util.ClientInit;
import com.weaver.agent.asm.util.HttpUtil;
import com.weaver.agent.asm.util.JsonUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * @author w
 * @date 2020-02-18 18:18
 */
public class SendTrackTask implements Runnable {
    private String ip;
    private String url;
    private IndexHashMap track;
    private HashMap sqls;
    private LinkedList trackindex;
    private IndexHashMap data;

    public SendTrackTask(String ip, String url, IndexHashMap track, HashMap sqls, LinkedList trackindex, IndexHashMap data) {
        this.ip = ip;
        this.url = url;
        this.track = track;
        this.sqls = sqls;
        this.trackindex = trackindex;
        this.data = data;
    }

    @Override
    public void run() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("ip", ip);
        map.put("url", url);
        map.put("track", JsonUtil.toJson(track));
        map.put("sqls", JsonUtil.toJson(sqls));
        map.put("trackindex", JsonUtil.toJson(trackindex));
        map.put("data", JsonUtil.toJson(data));
        HttpUtil.sendPostOnJson(ClientConstant.CONS_INFO.get(5),JsonUtil.toJson(map));
        System.out.println("=======TrackOver!!!======");
    }
}
