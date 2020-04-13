package com.weaver.agent.asm.trace;

import javax.servlet.http.HttpServletRequest;

/**
 * @author w
 * @date 2020-03-16 10:31
 */
public class FilterAop {
    public static void methodBefore(Object req) {
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURL().toString();
        if(url.endsWith(".js")||url.endsWith(".css")||url.endsWith(".jpg")||url.endsWith(".png")||url.endsWith(".ttf")||url.endsWith(".woff")||url.endsWith(".html")){
            ThreadLocalData.requesturl.set(url);
        }
    }
}
