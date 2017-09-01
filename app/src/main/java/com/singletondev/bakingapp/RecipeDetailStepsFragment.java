package com.singletondev.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class RecipeDetailStepsFragment2 extends Fragment{
    SimpleExoPlayer player;
    SimpleExoPlayerView playerView;
    BandwidthMeter bandwidthMeter;
    List<Steps> listSteps;
    Integer selectedIndex = 0;
    android.os.Handler handler;
    List<resep> recipe;
    String recipeName;

    ListItemClickListener listItemClickListener;

    interface ListItemClickListener{
        void onListItemClick(List<Steps> steps,Integer index, String recipeName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        handler = new android.os.Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        listItemClickListener = (RecipeDetailACtivity) getActivity();
        recipe = new ArrayList<>();

        if (savedInstanceState != null){
            listSteps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            recipeName = savedInstanceState.getString("Title");
        } else {
            if (getArguments().getParcelableArrayList(SELECTED_STEPS) != null){
                listSteps = getArguments().getParcelableArrayList(SELECTED_STEPS);
                selectedIndex = getArguments().getInt(SELECTED_INDEX);
                recipeName = getArguments().getString("Title");
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

        playerView = view.findViewById(R.id.simpleExoPlayer);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String stringURL = listSteps.get(selectedIndex).getVideoURL();

        if (TextUtils.isEmpty(stringURL)){
            player = null;
            playerView.setForeground(ContextCompat.getDrawable(getContext(),R.drawable.youtube_fail2));
            playerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

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
            TrackSelection.Factory videoTrackSelect = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(handler,videoTrackSelect);
            DefaultLoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(),defaultTrackSelector,loadControl);
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
    }

    
}
