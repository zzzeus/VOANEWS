package com.example.zhou.voanews.tools;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by Zhou on 2017/9/5.
 */
public class StringHelperTest {
    static Pattern p_char = Pattern.compile("[^a-zA-Z]+");//匹配字母
    @Test
    public  void getWordsFormString(){
        String s="Azerbaijan’s";

        String[] ss=p_char.split(s);
        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i]);
        }
        System.out.println(ss.length);

        getLongestOne(ss);

    }
    public  String getLongestOne(String[] ss){
        int pos=0;
        int len=0;
        for (int i = 0; i <ss.length ; i++) {
            if(ss[i].length()>len){
                System.out.println("--" + ss[i]);
                pos=i;
                len=ss[i].length();
            }

        }
        return ss[pos];
    }
}