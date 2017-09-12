package com.example.zhou.voanews;

import com.example.zhou.voanews.tools.DownloadHelper;

import org.junit.Test;

import okhttp3.Headers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void getText(){

                String url="https://dict.hjapi.com/v10/dict/en/cn";
                Headers headers=new Headers.Builder().add("Accept-Encoding","gzip")
                        .add("Content-Type","application/x-www-form-urlencoded")
                        .add("Host","dict.hjapi.com")
                        .add("User-Agent","HJApp 1.0/android/H30-C00/0D31F55E75637E632055961/4.4.2/com.hujiang.dict/2.8.7.162/zhihuiyun/")
                        .add("hujiang-appkey","b458dd683e237054f9a7302235dee675")
                        .add("hujiang-appsign","C2C5558418476872ABFF982B93617C9D").build();
           String t=     DownloadHelper.postCallback(url,"word=album",headers);
        System.out.println(t);


    }
}