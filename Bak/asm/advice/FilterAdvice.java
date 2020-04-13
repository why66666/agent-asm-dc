package com.weaver.agent.asm.advice;

import com.weaver.agent.asm.entity.TrackCounter;
import net.bytebuddy.asm.Advice;
import javax.servlet.http.HttpServletRequest;

/**
 * @author w
 * @date 2020-02-26 17:13
 */
public class FilterAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Argument(value = 0) Object req) {
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURL().toString();
        if(url.endsWith(".js")||url.endsWith(".css")||url.endsWith(".jpg")||url.endsWith(".png")||url.endsWith(".ttf")||url.endsWith(".woff")||url.endsWith(".html")){
            TrackCounter.staticEx.set(false);
        }
        TrackCounter.requesturl.set(url);
    }
}
