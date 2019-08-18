package com.example.apit.task.utils;

public class StringToSubstring {
    public static String StringToSubstring2(String res2){
        String res = res2.replace("[","");
        res = res.replace("\"{","{");
        res = res.replace("}\"","}");
        res = res.replace("\\","");
        res = res.replace("},{","}@#{");
        String crappyPrefix = "null";
        if(res.startsWith(crappyPrefix)){
            res = res.substring(crappyPrefix.length(), res.length());
        }
        String separated = res;
        return separated;
    }

}
