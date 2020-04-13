package com.weaver.agent.asm.trace;

import java.util.HashSet;
import java.util.Set;

/**
 * @author w
 * @date 2020-03-16 09:49
 */
public class RecordSetAop {
    public static void setSqls(String s) {
        int spanid = ThreadLocalData.methodStack.get().peek().getS();
        Set<String> sqls = ThreadLocalData.sqlData.get().get(spanid);
        if(sqls!=null){
            sqls.add(s);
        }else {
            Set<String> set = new HashSet<>();
            set.add(s);
            ThreadLocalData.sqlData.get().put(spanid,set);
        }
    }
}
