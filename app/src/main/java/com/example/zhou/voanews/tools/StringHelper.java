package com.example.zhou.voanews.tools;

import java.util.regex.Pattern;


/**
 * Created by Zhou on 2017/9/5.
 */

public class StringHelper {
    static Pattern p_char = Pattern.compile("[^a-zA-Z]+");//匹配字母
    public static String[] getWordsFormString(String s){
        if(s==null||s.isEmpty())
            return null;
        String[] ss=p_char.split(s);

        return ss;
    }
    public static String getLongestOne(String[] ss){
        int pos=0;
        int len=0;
        for (int i = 0; i <ss.length ; i++) {
            int l=ss[i].length();
            if(l>len){
                pos=i;
                len=l;
            }

        }
        return ss[pos];
    }
}
