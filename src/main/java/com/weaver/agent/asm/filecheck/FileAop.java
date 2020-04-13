package com.weaver.agent.asm.filecheck;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileAop {
    public static void methodBefore(File file) {
        Map<Integer,Set<String>> fds = ThreadLocalData.filedelSpan.get();
        Integer spanid = com.weaver.agent.asm.trace.ThreadLocalData.getMethodStack().get().peek().getS();
        Set<String> fls = fds.get(spanid);
        if(fls!=null){
            fls.add(file.getPath());
        }else {
            Set<String> fs = new HashSet<>();
            fs.add(file.getPath());
            fds.put(spanid,fs);
        }
    }
}
