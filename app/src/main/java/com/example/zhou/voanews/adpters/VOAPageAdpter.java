package com.example.zhou.voanews.adpters;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.zhou.voanews.activities.VOAPageActivity;
import com.example.zhou.voanews.data.json.VOAPageData;
import com.example.zhou.voanews.data.json.VOAPageItem;
import com.example.zhou.voanews.R;
import com.example.zhou.voanews.tools.StringHelper;

import java.util.ArrayList;

/**
 * Created by Zhou on 8/20/2017.
 */

public class VOAPageAdpter extends BaseAdpter {

    private VOAPageData voaPageData;
    public ArrayList<VOAPageItem> list=new ArrayList<VOAPageItem>();
    OnShowDetail onShowDetail;

    public VOAPageAdpter(String url,OnShowDetail onShowDetail) {
        super(url);
        this.onShowDetail=onShowDetail;
        update();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voapage,parent,false);
        return new VOAPageViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VOAPageViewHolder voaPageViewHolder= (VOAPageViewHolder) holder;
        VOAPageItem voaPageItem=list.get(position);
        final String a=voaPageItem.getSentence();
        voaPageViewHolder.chText.setText(voaPageItem.getSentence_cn());
        voaPageViewHolder.enText.setText(a);
        voaPageViewHolder.enText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d(TAG,"...start...");
                TextView textView= (TextView) view;
//                int position = text.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
//                String s = a.substring(a.lastIndexOf(" ", position), a.indexOf(" ", position));

                //                float x=motionEvent.getX();
//                float y=motionEvent.getY();
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_UP:
//                        int i=textView.getOffsetForPosition(x,y);
//                        Log.d(TAG,"......"+textView.getText().charAt(i));
//                }
                int position = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                if(a.charAt(position)==' ')
                    return false;
                int start=a.lastIndexOf(" ",position);
                int end=a.indexOf(" ", position);
                String s;
                if(start==-1&&end==-1)
                    s=a;
                else if(start==-1)
                    s=a.substring(0,end);
                else if(end==-1)
                    s=a.substring(start,a.length());
                else
                    s = a.substring(a.lastIndexOf(" ", position), a.indexOf(" ", position));
//                testSelected.setText(s);
//                Log.d(TAG, "onTouch: "+s);
                s=StringHelper.getLongestOne(StringHelper.getWordsFormString(s));
//                Log.d(TAG, "onTouch: ...."+s.length());
//                VOAPageActivity.cm.setPrimaryClip(ClipData.newPlainText("simple text",s));
                onShowDetail.getword(s);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void doUpdate(String u) {
//        Log.d(TAG, "doUpdate: "+u);
        voaPageData=JSON.parseObject(u,VOAPageData.class);

        list.clear();
        list.addAll(voaPageData.getData());
        Log.d(TAG, "doUpdate: "+list.size());
        notifyDataSetChanged();
    }

    public class VOAPageViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView enText;
        public TextView chText;
        public VOAPageViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
            enText=itemView.findViewById(R.id.item_voapage_eng);
            chText=itemView.findViewById(R.id.item_voapage_chi);
        }
    }
    public interface OnShowDetail{
        public void getword(String word);
    }
}
