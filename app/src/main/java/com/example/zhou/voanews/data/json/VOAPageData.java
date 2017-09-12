package com.example.zhou.voanews.data.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhou on 8/20/2017.
 */

public class VOAPageData {
    private int total;
    public List<VOAPageItem> data=new ArrayList<VOAPageItem>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<VOAPageItem> getData() {
        return data;
    }

    public void setData(List<VOAPageItem> list) {
        this.data = list;
    }
    public void addData(VOAPageItem v){
        data.add(v);
    }
}
