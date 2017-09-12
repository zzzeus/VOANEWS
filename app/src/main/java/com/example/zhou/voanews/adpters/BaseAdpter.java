package com.example.zhou.voanews.adpters;

import android.support.v7.widget.RecyclerView;

import com.example.zhou.voanews.tools.DownloadHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhou on 8/20/2017.
 */

public abstract class BaseAdpter extends RecyclerView.Adapter {
    public static final String TAG = "BaseAdpter";
    public String url;
    BaseAdpter(String url){
        this.url=url;
    }
    public void update(){
//        Log.d(TAG, "update: starting");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(toUpdate());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//                        Log.d(TAG, "accept: "+s);
                        doUpdate(s);
                    }
                });
    }
    public  String toUpdate(){
        if(url==null){
            return null;
        }
        return DownloadHelper.getPageText(url);
    };
    public abstract void doUpdate(String s);
}
