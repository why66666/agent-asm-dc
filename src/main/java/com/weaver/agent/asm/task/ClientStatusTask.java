package com.weaver.agent.asm.task;

import com.weaver.agent.asm.util.ClientInit;
import com.weaver.agent.asm.util.ClientConstant;
import com.weaver.agent.asm.util.HttpUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ClientStatusTask implements Runnable{
    @Override
    public void run() {
        System.out.println("--------------ConsumerStatu-------------");
        System.out.println("--------------Cookie:"+ ClientConstant.CONS_INFO.get(3));
        HttpUtil.sendGet(ClientConstant.CONS_INFO.get(7));
    }
}
