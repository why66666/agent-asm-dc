package com.weaver.agent.asm.depend;

import com.weaver.agent.asm.entity.ThreadStack;
import com.weaver.agent.asm.entity.Track;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author w
 * @date 2020-03-10 10:05
 */
public class SqlUtil {
    public static void setSqls(String s){
        int spanid = ThreadStack.get().peek().getS();
        Set<String> sqls = Track.sqlData.get().get(spanid);
        if(sqls!=null){
            sqls.add(s);
        }else {
            Set<String> set = new HashSet<>();
            set.add(s);
            Track.sqlData.get().put(spanid,set);
        }
    }
}
