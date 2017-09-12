package com.example.zhou.voanews.tools;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zhou on 8/20/2017.
 */
public class DownloadHelperTest {
    private static OkHttpClient okHttpClient=new OkHttpClient();

    public  String getPageText(String url) {
        String text=null;
        try {
            Response r =okHttpClient.newCall(new Request.Builder().url(url).build()).execute();
            Assert.assertEquals(r.isSuccessful(), true);
            text=r.body().string();
            System.out.println(text);// 固定tag是System.out
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @Test
    public  void DoTest(){
        getPageText("http://apps.iyuba.com/iyuba/titleApi3.jsp?maxid=0&pages=1&pageNum=20&parentID=0&type=android&format=json");
    }

}