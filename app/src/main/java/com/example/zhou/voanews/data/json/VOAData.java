package com.example.zhou.voanews.data.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhou on 8/20/2017.
 */

public class VOAData {
//    public List<VOAItem> list=new ArrayList<VOAItem>();
//    public int total;
//
//    public List<VOAItem> getList() {
//        return list;
//    }
//
//    public void setList(ArrayList<VOAItem> list) {
//        this.list = list;
//    }
//    public void addList(VOAItem voaItem){
//        list.add(voaItem);
//    }
//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }

    private int total;
    public List<VOAItem> data=new ArrayList<VOAItem>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<VOAItem> getData() {
        return data;
    }

    public void setData(List<VOAItem> list) {
        this.data = list;
    }
    public void addData(VOAItem v){
        data.add(v);
    }
}
