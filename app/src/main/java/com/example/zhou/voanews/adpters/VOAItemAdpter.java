package com.example.zhou.voanews.adpters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.zhou.voanews.data.json.VOAData;
import com.example.zhou.voanews.data.json.VOAItem;
import com.example.zhou.voanews.R;
import com.example.zhou.voanews.tools.DownloadHelper;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhou on 8/20/2017.
 */

public class VOAItemAdpter extends RecyclerView.Adapter {
    private static final String TAG = "VOAItemAdpter";
    public static final String CSVOA="http://apps.iyuba.com/iyuba/titleChangSuApi3.jsp?maxid=0&pages=1&pageNum=20&parentID=0&type=android&format=json";
    public static final String MSVOA="http://apps.iyuba.com/iyuba/titleApi3.jsp?maxid=0&pages=1&pageNum=20&parentID=0&type=android&format=json";

    public VOAData voaData;
    public final static ArrayList<VOAItem> arrayList=new ArrayList<VOAItem>();

    public String url;
    private int cat;
    private OnMyClickListener onMyClickListener;
    public interface OnMyClickListener {
        public void onClick(int num);
        public void onStartActivity(int pos);
    }
    public  VOAItemAdpter(String u,OnMyClickListener onMyClickListener){
        url=u;
        this.onMyClickListener=onMyClickListener;
        update();
    }
    public void update(){
        if(url==null){
            return;
        }
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String text=DownloadHelper.getPageText(url);
                Log.d(TAG, "subscribe: "+text);
                e.onNext(text);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s!=null){
                            voaData=JSON.parseObject(s,VOAData.class);
                            arrayList.clear();
                            arrayList.addAll(voaData.getData());
                            notifyDataSetChanged();
                        }
                    }});
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voaitem,parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        final VOAItem voaItem=arrayList.get(position);
        myViewHolder.title.setText(voaItem.getTitle());
        myViewHolder.info.setText(voaItem.getCreatTime().substring(0,10));
        Glide.with(myViewHolder.imageView.getContext()).load(voaItem.getPic()).into(myViewHolder.imageView);
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+voaItem.getVoaId());
                onMyClickListener.onStartActivity(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView info;
        ImageButton star;
        ImageButton download;

        View rootView;
        public MyViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
            download=itemView.findViewById(R.id.item_voaitem_download);
            imageView=itemView.findViewById(R.id.item_voaitem_imageView);
            title=itemView.findViewById(R.id.item_voaitem_title);
            info=itemView.findViewById(R.id.item_voaitem_info);
            star=itemView.findViewById(R.id.item_voaitem_star);
        }
    }
}
