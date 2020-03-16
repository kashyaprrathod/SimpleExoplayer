package com.usetime.exoplayer_example;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.concurrent.TimeUnit;

public class Watchvideo extends AppCompatActivity {

    SimpleExoPlayerView exoPlayerview;
    ExoPlayer exoPlayer;
    MediaSource mediaSource;
    
    String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchvideo);

        exoPlayerview =  findViewById(R.id.exoplayer);
        
        url = getIntent().getStringExtra("url");
        
        setExoplayer();
    }

    private void setExoplayer() {

        //Code that you have to complusuty add for use exoplayer:-
        //Compulsary code------------------//

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(Watchvideo.this, "exoplayer");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);


        TrackSelector trackSelector = new DefaultTrackSelector();

        exoPlayer = ExoPlayerFactory.newSimpleInstance(Watchvideo.this, trackSelector);
        exoPlayerview.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayerview.setUseController(true);


        //Compulsary code---------------------//


        //For Play video:-
//        exoPlayer.setPlayWhenReady(true);


        //For Seek to specific position :-
//        seekto();


        exoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == Player.STATE_READY){

                    //what to do when player is ready
                }
                if (playbackState == Player.STATE_ENDED) {
                    //what to do when Video is ended
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();

        //release Exoplayer when back on close the app for stop playing video in background
        exoPlayer.release();
    }
}
