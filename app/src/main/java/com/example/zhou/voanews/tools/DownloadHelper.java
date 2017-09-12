package com.example.zhou.voanews.tools;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Zhou on 8/20/2017.
 */

public class DownloadHelper {
    private static OkHttpClient okHttpClient=new OkHttpClient();
    public static String getPageText(String url){
        String text=null;
        try {
            text=okHttpClient.newCall(new Request.Builder().url(url).build()).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    public static void getPageTextCallback(String url, Callback callback){
        okHttpClient.newCall(new Request.Builder().url(url).build()).enqueue(callback);
    }
    public static void getPageTextCallback(String url, Headers headers, Callback callback){
        okHttpClient.newCall(new Request.Builder().url(url).build()).enqueue(callback);
    }
    public static void postPageTextCallback(String url, RequestBody rb, Headers headers, Callback callback){
        okHttpClient.newCall(new Request.Builder().url(url).headers(headers).post(rb).build()).enqueue(callback);
    }
    public static String postCallback(String url, String s, Headers headers){
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),s);
        String a="";
        try {
            a=okHttpClient.newCall(new Request.Builder().url(url).headers(headers).post(requestBody).build()).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }

}
