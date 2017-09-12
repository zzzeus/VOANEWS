package com.example.zhou.voanews.adpters;

import com.example.zhou.voanews.tools.DownloadHelper;

import org.junit.Test;

/**
 * Created by Zhou on 8/20/2017.
 */
public class BaseAdpterTest {
    @Test
    public void update() throws Exception {

    }

    @Test
    public void toUpdate() throws Exception {
        System.out.println(DownloadHelper.getPageText("http://apps.iyuba.com/iyuba/textNewApi.jsp?voaid=5452&format=json"));
    }

}