package com.singletondev.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.singletondev.bakingapp.modelData.Steps;
import com.singletondev.bakingapp.modelData.resep;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static com.singletondev.bakingapp.Besa.SELECTED_INDEX;
import static com.singletondev.bakingapp.Besa.SELECTED_RECIPES;
import static com.singletondev.bakingapp.Besa.SELECTED_STEPS;

/**
 * Created by Randy Arba on 8/29/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */


public class RecipeDetailStepsFragment extends Fragment{

    public static String SELECTED_POSITION = "SELECTED_POSITION";
    SimpleExoPlayer player;
    SimpleExoPlayerView playerView;
    BandwidthMeter bandwidthMeter;
    List<Steps> listSteps;
    Integer selectedIndex = 0;
    android.os.Handler handler;
    List<resep> recipe;
    String recipeName;
    ImageView buttonFull;
    Long position;
    String stringURL;

    ListItemClickListener listItemClickListener;

    interface ListItemClickListener{
        void onListItemClick(List<Steps> steps,int index, String recipeName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        handler = new android.os.Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        listItemClickListener = (RecipeDetailACtivity) getActivity();
        recipe = new ArrayList<>();

        Log.e("CreatePost","Kali");

        position = C.TIME_UNSET;
        if (savedInstanceState != null){
            listSteps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            recipeName = savedInstanceState.getString("Title");
            position = savedInstanceState.getLong(SELECTED_POSITION);
            Log.e("newPosition",String.valueOf(position));

        } else {
            Log.e("PostitionValid","notValid");
            if (getArguments().getParcelableArrayList(SELECTED_STEPS) != null){
                listSteps = getArguments().getParcelableArrayList(SELECTED_STEPS);
                selectedIndex = getArguments().getInt(SELECTED_INDEX);
                recipeName = getArguments().getString("Title");
                position = getArguments().getLong(SELECTED_POSITION);
            } else {
                recipe = getArguments().getParcelableArrayList(SELECTED_RECIPES);
                listSteps = recipe.get(0).getSteps();
                selectedIndex = 0;
                recipeName = recipe.get(0).getName();
            }
        }

        View view = inflater.inflate(R.layout.fragment_recipe_detail_steps,container,false);
        TextView textView = (TextView) view.findViewById(R.id.descriptionSteps);
        Log.e("Description","desc");
        textView.setText(listSteps.get(selectedIndex).getDescription());
        textView.setVisibility(View.VISIBLE);

        buttonFull = view.findViewById(R.id.btn_full);

        playerView = view.findViewById(R.id.simpleExoPlayer);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        stringURL = listSteps.get(selectedIndex).getVideoURL();

        if (TextUtils.isEmpty(stringURL)){
            player = null;
            playerView.setForeground(ContextCompat.getDrawable(getContext(),R.drawable.youtube_fail));
            //playerView.setLayoutParams(new LinearLayout.LayoutParams(300,300));

            if (view.findViewWithTag("view-land") != null){
                Log.e("View1","view-land");
                getActivity().findViewById(R.id.fragment_container2).setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
                view.findViewById(R.id.rl_navigation).setVisibility(View.GONE);
            } else  if (isInLandscapeMode(getContext())){
                Log.e("View2","View-Land");
                textView.setVisibility(View.GONE);
                view.findViewById(R.id.rl_navigation).setVisibility(View.GONE);
            }
        } else {
            Log.e("Execute","execute");
            Log.e("Position",String.valueOf(position));
            initPlayer(Uri.parse(listSteps.get(selectedIndex).getVideoURL()));
            if (view.findViewWithTag("view-land") != null){
                Log.e("View1","view-land");
                getActivity().findViewById(R.id.fragment_container2).setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
                view.findViewById(R.id.rl_navigation).setVisibility(View.GONE);
            } else if (isInLandscapeMode(getContext())){
                Log.e("View2","view-land");
                textView.setVisibility(View.GONE);
                view.findViewById(R.id.rl_navigation).setVisibility(View.GONE);
            }

        }

        buttonFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),FullscreenDialog.class);
                String url = listSteps.get(selectedIndex).getVideoURL();
                intent.putExtra("StringURL",url);
                startActivity(intent);
            }
        });

        TextView backButton = (TextView) view.findViewById(R.id.backButton);
        TextView nextButton = (TextView) view.findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSteps.get(selectedIndex).getId() > 0){
                    if (player != null){
                        player.stop();
                    }
                    listItemClickListener.onListItemClick(listSteps,listSteps.get(selectedIndex).getId() - 1,recipeName);
                } else {
                    Toast.makeText(getContext(),"Sudah mencapai step pertama", Toast.LENGTH_SHORT).show();
                }
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer lastIndex = listSteps.size() - 1;
                if (listSteps.get(selectedIndex).getId() < listSteps.get(lastIndex).getId()){
                    if (player != null){
                        player.stop();
                    }
                    listItemClickListener.onListItemClick(listSteps,listSteps.get(selectedIndex).getId() +1,recipeName);
                } else {
                    Toast.makeText(getContext(), "Anda sudah mencapai akhir step", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public Boolean isInLandscapeMode(Context context){
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void initPlayer(Uri uri){
        if (player == null){
            Log.e("Execute","execute");
            TrackSelection.Factory videoTrackSelect = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(handler,videoTrackSelect);
            DefaultLoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(),defaultTrackSelector,loadControl);
            player.seekTo(position);
            Log.e("PostCond",String.valueOf(position));
            position = player.getCurrentPosition();

            Log.e("getPost",String.valueOf(player.getCurrentPosition()));
            playerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(),"BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory(getContext(),userAgent),new DefaultExtractorsFactory(),null,null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SELECTED_STEPS, (ArrayList<Steps>) listSteps);
        outState.putInt(SELECTED_INDEX, selectedIndex);
        outState.putString("Title",recipeName);
        outState.putLong(SELECTED_POSITION,position);
        Log.e("post",String.valueOf(position));
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlayer(Uri.parse(listSteps.get(selectedIndex).getVideoURL()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null){
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player != null){
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null){
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
       if (player != null){
           position = player.getCurrentPosition();

           Log.e("HasilPosisi",String.valueOf(position));

           player.stop();
           player.release();
           player= null;
       }
    }
}
