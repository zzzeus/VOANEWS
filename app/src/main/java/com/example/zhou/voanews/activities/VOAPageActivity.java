package com.example.zhou.voanews.activities;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zhou.voanews.adpters.VOAItemAdpter;
import com.example.zhou.voanews.adpters.VOAPageAdpter;
import com.example.zhou.voanews.data.json.VOAItem;
import com.example.zhou.voanews.data.json.VOAPageItem;
import com.example.zhou.voanews.R;
import com.example.zhou.voanews.data.json.VOAWord;
import com.example.zhou.voanews.tools.ExoplayerHelper;
import com.example.zhou.voanews.tools.translationUtils.VOAWordHelper;
import com.example.zhou.voanews.views.MusicContorller;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.TimeBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VOAPageActivity extends AppCompatActivity implements VOAPageAdpter.OnShowDetail {
    public static final String TAG = "VOAPageActivity";
    public static ClipboardManager cm;
    @BindView(R.id.voapage_recycler)
    RecyclerView voapageRecycler;
    public static final String url = "http://apps.iyuba.com/iyuba/textNewApi.jsp?voaid=%s&format=json";
    public static final String mp4url = "http://staticvip.iyuba.com/sounds/voa/%s";
    @BindView(R.id.voapage_controller)
    MusicContorller voapageController;
//    PlaybackControlView playbackControlView;
    private int id;
    private String url1;
    private VOAItem voaItem;
    public ExoplayerHelper exoplayerHelper;
    public VOAPageAdpter voaPageAdpter;
    LinearLayoutManager linearLM;
    public int pos=-1;

    public boolean isPlaying=true;
    ComponentListener componentListener=new ComponentListener();
    View chosenView;
    int shownNum=0;
    float triming=0;
    float endTriming=0;
    boolean isClosed=false;

    PopupWindow popupWindow;
    TextView popTitle;
    TextView popMore;
    TextView popDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voapage);
        ButterKnife.bind(this);

        initActionBar();
        init();
        if(cm==null){
            cm= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        }
    }

    private void init() {
        Intent intent = getIntent();
        id = intent.getIntExtra("pos", 0);
        voaItem = VOAItemAdpter.arrayList.get(id);
        url1 = String.format(url, voaItem.getVoaId());
//        Log.d(TAG, "init: "+url1);
        linearLM=new LinearLayoutManager(this);
        voapageRecycler.setLayoutManager(linearLM);
        voaPageAdpter=new VOAPageAdpter(url1,this);
        voapageRecycler.setAdapter(voaPageAdpter);

        initPlayer(String.format(mp4url, voaItem.getSound()));
        initPopup();
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("NEWS NAME");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id) {
            case android.R.id.home:
//                Toast.makeText(this, "Home is clicked!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return true;
    }

    private void initPlayer(String url) {
        exoplayerHelper = new ExoplayerHelper();

        exoplayerHelper.initWithController(this, url,componentListener);

        voapageController.voaPageActivity=this;
        voapageController.setListener(componentListener);


//        // Playback control view.
//        View controllerPlaceholder = findViewById(R.id.voapage_holder);
//        if (controllerPlaceholder != null) {
//            // Note: rewindMs and fastForwardMs are passed via attrs, so we don't need to make explicit
//            // calls to set them.
//            playbackControlView = new PlaybackControlView(this, null);
//            playbackControlView.setLayoutParams(controllerPlaceholder.getLayoutParams());
//            ViewGroup parent = ((ViewGroup) controllerPlaceholder.getParent());
//            int controllerIndex = parent.indexOfChild(controllerPlaceholder);
//            parent.removeView(controllerPlaceholder);
//            parent.addView(playbackControlView, controllerIndex);
//        } else {
//            this.playbackControlView = null;
//        }
//        playbackControlView.setShowTimeoutMs(0);
//        playbackControlView.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (exoplayerHelper != null) {
            exoplayerHelper.getExoPlayer().setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoplayerHelper != null) {
            exoplayerHelper.getExoPlayer().setPlayWhenReady(false);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isClosed=true;
        if (exoplayerHelper != null) {
            exoplayerHelper.release();
            exoplayerHelper=null;
        }
        voapageController.voaPageActivity=null;
    }

    @Override
    public void getword(String word) {
        Log.d(TAG, "getword: "+word);
        popTitle.setText(word);
        getWordMean(word);
        showPopup();
    }
    public void getWordMean(final String w){
        Observable.create(new ObservableOnSubscribe<VOAWord>() {
            @Override
            public void subscribe(ObservableEmitter<VOAWord> e) throws Exception {
                VOAWordHelper vwh=new VOAWordHelper();
                e.onNext(vwh.getMeaning(w));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VOAWord>() {
                    @Override
                    public void accept(VOAWord voaWord) throws Exception {
                        if(voaWord!=null&&voaWord.result==1){
                            popDetail.setText(voaWord.text);
                        }
                    }
                });
    }
    public final class ComponentListener implements ExoPlayer.EventListener, TimeBar.OnScrubListener,
            View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.play_pause:
                    exoplayerHelper.getExoPlayer().setPlayWhenReady(!exoplayerHelper.getExoPlayer().getPlayWhenReady());
                    voapageController.updateprogress();
//                    updatePlayView();
                    voapageController.updateprogress();
                    voapageController.updatePlayButton();
                    break;
                case R.id.prev:
                    break;
                case R.id.next:
                    break;

            }
        }

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.d(TAG, "onTimelineChanged: .....");
//            updateProgress();
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            // Do nothing.
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            // Do nothing.
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if(playbackState== SimpleExoPlayer.STATE_READY&&playWhenReady){
                isPlaying=true;
                voapageController.updateprogress();
                showHighlight(0);
//                changeLine1();
            }else if(playbackState==SimpleExoPlayer.STATE_ENDED){
                isPlaying=false;
            }
            voapageController.updatePlayButton();
//            Log.d(TAG, "onPlayerStateChanged: ...");
//            updatePlayView();
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            // Do nothing.
        }

        @Override
        public void onPositionDiscontinuity() {
//            updateProgress();
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }

        @Override
        public void onScrubStart(TimeBar timeBar) {
            Log.d(TAG, "onScrubStart: ...");
            timeBar.setDuration(exoplayerHelper.getExoPlayer().getDuration());
        }

        @Override
        public void onScrubMove(TimeBar timeBar, long position) {
            Log.d(TAG, "onScrubMove: ...");
        }

        @Override
        public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
            Log.d(TAG, "onScrubStop: ....."+position);

//            long l=
            exoplayerHelper.getExoPlayer().seekTo(position);
//            changeLine(position/1000);
        }
    }


    public  void changeLine1(){
        Log.d(TAG, "changeLine1: ........");
        if(isClosed||isPlaying==false)
            return;

        if(exoplayerHelper==null||!exoplayerHelper.getExoPlayer().getPlayWhenReady())
            return;

        long time=500;
        int i=getCurrentLine();
//        if(pos>-1&&voaPageAdpter.list.get(pos+1).Timing<p&&voaPageAdpter.list.get(pos+1).EndTiming>p){
//            voapageRecycler.scrollToPosition(pos+1);
//            showHighlight(pos);
//            pos=pos+1;
//        }else if(pos>-1&&voaPageAdpter.list.get(pos).Timing<p&&voaPageAdpter.list.get(pos).EndTiming>p){
//        }
//        else {
//            i=getCurrentLine();
//        }

        VOAPageItem v=voaPageAdpter.list.get(i);

        if(pos==i)
            time=1000;
        else {
            pos=i;
            float p=exoplayerHelper.getExoPlayer().getCurrentPosition()/1000;
            time=(long) (v.EndTiming-p)*1000;
        }

        Log.d(TAG, "changeLine1: "+time);
        voapageRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeLine1();
            }
        }, time);
    }

    public void changeLine(){
        float p=exoplayerHelper.getExoPlayer().getCurrentPosition()/1000;
        p+=0.5;
        if(pos>-1&&voaPageAdpter.list.get(pos).Timing<p&&voaPageAdpter.list.get(pos).EndTiming>p)
            return;
        int i=getCurrentLine();
        if(pos==i)
            return;
        voapageRecycler.scrollToPosition(i+1);
        showHighlight(i);
        pos=i;
//        double d=0;
//        for(int i=0;i<voaPageAdpter.list.size()-1;i++){
//            VOAPageItem v=voaPageAdpter.list.get(i);
//
//            if(v.Timing<l&&v.EndTiming>l){
//                if(pos==i)
//                    return;
//
//                voapageRecycler.scrollToPosition(i+1);
//                showHighlight(i);
//                pos=i;
//                return;
//
//            }
//
//        }
//        showHighlight(voaPageAdpter.list.size()-1);
    }
    public int getCurrentLine(){

        float p=exoplayerHelper.getExoPlayer().getCurrentPosition()/1000;
        for(int i=0;i<voaPageAdpter.list.size()-1;i++){
            VOAPageItem v=voaPageAdpter.list.get(i);

            if(v.Timing<p&&v.EndTiming>p){
                return i;

            }}
        return voaPageAdpter.list.size()-1;
    }
    public void showHighlight(int i){
        Log.d(TAG, "showHighlight: trying to highlight the: "+i);
      if(chosenView !=null){
          VOAPageAdpter.VOAPageViewHolder vh= (VOAPageAdpter.VOAPageViewHolder) voapageRecycler.getChildViewHolder(chosenView);
          vh.enText.setTextColor(Color.BLACK);
          vh.chText.setTextColor(Color.BLACK);
      }
        chosenView =linearLM.findViewByPosition(i);

        if(chosenView!=null){
            VOAPageAdpter.VOAPageViewHolder vh= (VOAPageAdpter.VOAPageViewHolder) voapageRecycler.getChildViewHolder(chosenView);
            vh.enText.setTextColor(Color.BLUE);
            vh.chText.setTextColor(Color.BLUE);
        }else {
            Log.d(TAG, "showHighlight: .......view is null..");
        }

    }

    public void initPopup(){
//        // init the popup window
        View v = LayoutInflater.from(this).inflate(R.layout.pop_wordmeaning, null);

        popTitle=v.findViewById(R.id.pop_wm_title);
        popDetail=v.findViewById(R.id.pop_wm_detail);
        popMore=v.findViewById(R.id.pop_wm_more);

        // 创建PopupWindow对象，指定宽度和高度
        popupWindow = new PopupWindow(v, 500, 400);
        // 设置动画
//        window.setAnimationStyle(R.style.popup_window_anim);
        // 设置背景颜色
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //  设置可以获取焦点
        popupWindow.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        // 更新popupwindow的状态
        popupWindow.update();

    }
    public void showPopup(){
        //  2017/9/6  let the popup window show
//        Log.d(TAG, "showPopup: ...........");

        popupWindow.showAtLocation(voapageRecycler, Gravity.CENTER,0,0);

    }
}
