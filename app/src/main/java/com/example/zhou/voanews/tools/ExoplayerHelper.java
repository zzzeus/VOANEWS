package com.example.zhou.voanews.tools;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

/**
 * Created by Zhou on 8/20/2017.
 */

public class ExoplayerHelper {

        public static SimpleExoPlayer exoPlayer;

        public ExoplayerHelper(){
            if(exoPlayer!=null){
                exoPlayer.release();
                exoPlayer=null;
            }


        }

        public void init(Context context, String uri, ExoPlayer.EventListener eventListener){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector=new DefaultTrackSelector(videoTrackSelectionFactory);
            exoPlayer= ExoPlayerFactory.newSimpleInstance(context,trackSelector);
            exoPlayer.addListener(eventListener);

// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "com.development.zhou.languagelearning"));
// Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource(Uri.parse(uri),
                    dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(true);

        }
        public void initViewPlayer(Context context,String url,SimpleExoPlayerView v){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector=new DefaultTrackSelector(videoTrackSelectionFactory);
            exoPlayer= ExoPlayerFactory.newSimpleInstance(context,trackSelector);
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "com.development.zhou.languagelearning"));
// Produces Extractor instances for parsing the media data.
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.

            HlsMediaSource hlsMediaSource=new HlsMediaSource(Uri.parse(url), dataSourceFactory, 2, new Handler(),
                    new AdaptiveMediaSourceEventListener() {
                        @Override
                        public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs) {

                        }

                        @Override
                        public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

                        }

                        @Override
                        public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

                        }

                        @Override
                        public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {

                        }

                        @Override
                        public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {

                        }

                        @Override
                        public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaTimeMs) {

                        }
                    });

//        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(uri),
//                dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.prepare(hlsMediaSource);
            exoPlayer.setPlayWhenReady(true);
            v.setPlayer(exoPlayer);
        }
        public void initWithController(Context context, String url, PlaybackControlView playbackControlView){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector=new DefaultTrackSelector(videoTrackSelectionFactory);
            exoPlayer= ExoPlayerFactory.newSimpleInstance(context,trackSelector);
            playbackControlView.setPlayer(exoPlayer);

// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "com.development.zhou.languagelearning"));
// Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                    dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(true);
        }

        public void stop(){
            if(exoPlayer!=null)
            {
                exoPlayer.stop();
            }
        }
        public void release(){
            if (exoPlayer != null) {
                exoPlayer.release();
                exoPlayer=null;
            }
        }
        public void initWithController(Context context, String url ,ExoPlayer.EventListener eventListener){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector=new DefaultTrackSelector(videoTrackSelectionFactory);
            exoPlayer= ExoPlayerFactory.newSimpleInstance(context,trackSelector);


// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "com.development.zhou.languagelearning"));
// Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                    dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(true);

            exoPlayer.addListener(eventListener);
        }
        public SimpleExoPlayer getExoPlayer(){
            return exoPlayer;
        }
}
