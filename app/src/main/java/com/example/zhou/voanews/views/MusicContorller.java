package com.example.zhou.voanews.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhou.voanews.activities.VOAPageActivity;
import com.example.zhou.voanews.R;
import com.example.zhou.voanews.tools.ExoplayerHelper;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.DefaultTimeBar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhou on 2017/08/23.
 */

public class MusicContorller extends LinearLayout {



    public TextView startText;

    public DefaultTimeBar timeBar;

    public TextView endText;

    public ImageView prev;

    public ImageView playPause;

    public ImageView next;

    View myView;

    public VOAPageActivity voaPageActivity;

    SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    public MusicContorller(Context context) {
        super(context);
        LayoutInflater mInflater = LayoutInflater.from(context);
         myView = mInflater.inflate(R.layout.view_musiccontroller, null);
        addView(myView);
    }

    public MusicContorller(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);
         myView = mInflater.inflate(R.layout.view_musiccontroller, null);
        addView(myView);
    }

    public MusicContorller(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = LayoutInflater.from(context);
         myView = mInflater.inflate(R.layout.view_musiccontroller, null);
        addView(myView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        startText=myView.findViewById(R.id.startText);
        endText=myView.findViewById(R.id.endText);
        timeBar=myView.findViewById(R.id.timebar);
        prev=myView.findViewById(R.id.prev);
        playPause=myView.findViewById(R.id.play_pause);
        next=myView.findViewById(R.id.next);
    }
    public void setListener(VOAPageActivity.ComponentListener componentListener){

        timeBar.setListener(componentListener);
        next.setOnClickListener(componentListener);
        playPause.setOnClickListener(componentListener);
        prev.setOnClickListener(componentListener);

//        timeBar.setEnabled(true);
//        timeBar.setDuration(1000);
//        timeBar.setPosition(20);
//        timeBar.setBufferedPosition(30);
    }

    public void updatePlayButton(){
        ExoplayerHelper exoplayerHelper=voaPageActivity.exoplayerHelper;
        if(exoplayerHelper== null)
            return;
        ExoPlayer exoPlayer=exoplayerHelper.getExoPlayer();
        if(exoPlayer==null)
            return;
        if(exoPlayer.getPlayWhenReady()==true)
        playPause.setImageResource(R.drawable.uamp_ic_pause_white_48dp);
        else
        playPause.setImageResource(R.drawable.uamp_ic_play_arrow_white_48dp);
    }
    public void updateprogress(){
        if(voaPageActivity==null){
            return;
        }
        ExoplayerHelper exoplayerHelper=voaPageActivity.exoplayerHelper;
        if(exoplayerHelper== null)
            return;
        ExoPlayer exoPlayer=exoplayerHelper.getExoPlayer();
        if(exoPlayer==null)
            return;
        if(voaPageActivity.isPlaying==false)
            return;

//        Log.d(VOAPageActivity.TAG, "updateprogress: "+exoPlayer.getPlayWhenReady());
//        updatePlayButton();
        long position = 0;
        long bufferedPosition = 0;
        long duration = 0;



        position = exoPlayer.getCurrentPosition();
        bufferedPosition = exoPlayer.getBufferedPosition();
        duration = exoPlayer.getDuration();
        if (timeBar != null) {
            timeBar.setPosition(position);
            timeBar.setBufferedPosition(bufferedPosition);
            timeBar.setDuration(duration);
        }
        Date d1=new Date(exoPlayer.getCurrentPosition());
        String t1=format.format(d1);
        startText.setText(t1);
        long l=exoPlayer.getDuration();

        d1=new Date(l);
        t1=format.format(d1);
        endText.setText(t1);
//        Log.d(VOAPageActivity.TAG, "updateprogress: "+position);
        voaPageActivity.changeLine();


        postDelayed(new Runnable() {
            @Override
            public void run() {
                updateprogress();
            }
        },1000);
    }
}
