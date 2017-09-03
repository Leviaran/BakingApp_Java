package com.singletondev.bakingapp;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class FullscreenDialog extends AppCompatActivity {

    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;
    BandwidthMeter bandwidthMeter;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_dialog);

        String stringURL = getIntent().getStringExtra("StringURL");
        Log.e("stringURL",stringURL);

        playerView = (SimpleExoPlayerView) findViewById(R.id.simpleExoPlayer);
        handler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        if (TextUtils.isEmpty(stringURL)){
            player = null;
            playerView.setForeground(ContextCompat.getDrawable(getBaseContext(),R.drawable.youtube_fail));
        } else {
            if (player == null){
                TrackSelection.Factory trackSelection = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
                DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(handler,trackSelection);
                DefaultLoadControl defaultLoadControl = new DefaultLoadControl();

                player = ExoPlayerFactory.newSimpleInstance(getBaseContext(),defaultTrackSelector,defaultLoadControl);
                playerView.setPlayer(player);

                String userAgent = Util.getUserAgent(getBaseContext(),"BakingApp");
                Uri uri = Uri.parse(stringURL);
                MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory(getBaseContext(),userAgent),new DefaultExtractorsFactory(),null,null);
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
            }
        }




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null){
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null){
            player.stop();
            player.release();
        }
    }
}
