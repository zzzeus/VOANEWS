package com.example.zhou.voanews.adpters;

import com.alibaba.fastjson.JSON;
import com.example.zhou.voanews.data.json.VOAData;
import com.example.zhou.voanews.tools.DownloadHelper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zhou on 8/20/2017.
 */
public class VOAItemAdpterTest {
    public VOAData voaData;
    @Test
    public void update() throws Exception {
        String url="http://apps.iyuba.com/iyuba/titleChangSuApi3.jsp?maxid=0&pages=1&pageNum=20&parentID=0&type=android&format=json";
        url="http://apps.iyuba.com/iyuba/titleApi3.jsp?maxid=0&pages=1&pageNum=20&parentID=0&type=android&format=json";
        String text=DownloadHelper.getPageText(url);
//        System.out.println(text);
        if(text!=null){
            voaData= JSON.parseObject(text,VOAData.class);
//            System.out.println(voaData.getTotal());
            assertEquals(20,voaData.getData().size());
        }
    }

}