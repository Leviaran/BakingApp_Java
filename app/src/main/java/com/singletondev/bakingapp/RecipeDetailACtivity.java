package com.singletondev.bakingapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.singletondev.bakingapp.Adapters.RecipeDetailAdapter;
import com.singletondev.bakingapp.modelData.Steps;
import com.singletondev.bakingapp.modelData.resep;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.singletondev.bakingapp.Besa.SELECTED_RECIPES;

/**
 * Created by Randy Arba on 8/29/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RecipeDetailACtivity extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListerer, RecipeDetailStepsFragment.ListItemClickListener {

    static String ALL_RECIPES="All_Recipes";
    static String SELECTED_RECIPES="Selected_Recipes";
    static String SELECTED_STEPS="Selected_Steps";
    static String SELECTED_INDEX="Selected_Index";
    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";
    static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";

    List<resep> recipe;
    String recipeName;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appbar;
    Boolean flag;
    Toolbar toolbar;


    @Override
    public void onListItemClick(List<Steps> step, int clickItemIndex, String recipeName) {
        RecipeDetailStepsFragment recipeDetailStepsFragment = new RecipeDetailStepsFragment();
        FragmentManager fm = getSupportFragmentManager();
        appbar.setExpanded(false);
        collapsingToolbarLayout.setTitle(recipeName);


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Steps>) step);
        bundle.putInt(SELECTED_INDEX,clickItemIndex);
        bundle.putString("Title",recipeName);
        recipeDetailStepsFragment.setArguments(bundle);

        if (findViewById(R.id.recipe).getTag() != null && findViewById(R.id.recipe).getTag().equals("view-land")){
            fm.beginTransaction()
                    .replace(R.id.fragment_container2,recipeDetailStepsFragment)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }else {
            fm.beginTransaction()
                    .replace(R.id.fragment_container,recipeDetailStepsFragment)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_activity);

        if (savedInstanceState == null){
            Bundle selectedBundle = getIntent().getExtras();
            recipe = new ArrayList<>();
            recipe = selectedBundle.getParcelableArrayList(SELECTED_RECIPES);
            recipeName = recipe.get(0).getName();

            Log.e("fragment1","aktif");

            DetailFragment fragmentDetail = new DetailFragment();
            fragmentDetail.setArguments(selectedBundle);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragment_container,fragmentDetail)
                    .addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe).getTag() != null && findViewById(R.id.recipe).getTag().equals("view-land")){
                Log.e("Fragment2","aktif");

                RecipeDetailStepsFragment fragment2 = new RecipeDetailStepsFragment();
                fragment2.setArguments(selectedBundle);
                fm.beginTransaction()
                        .replace(R.id.fragment_container2,fragment2)
                        .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();
            }
        } else {
            Bundle selectedBundle = getIntent().getExtras();
            recipe = new ArrayList<>();
            recipe = selectedBundle.getParcelableArrayList(SELECTED_RECIPES);
            recipeName =recipe.get(0).getName();
            Log.e("recipeName",recipe.get(0).getName());


            DetailFragment fragmentDetail = new DetailFragment();
            fragmentDetail.setArguments(selectedBundle);
            FragmentManager fman = getSupportFragmentManager();
            fman.beginTransaction()
                    .replace(R.id.fragment_container,fragmentDetail)
                    .addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe).getTag() != null && findViewById(R.id.recipe).getTag().equals("view-land")){
                Log.e("Fragment3","aktif");
                RecipeDetailStepsFragment fragment3 = new RecipeDetailStepsFragment();
                fragment3.setArguments(selectedBundle);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_container2,fragment3)
                        .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();
            }
        }

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout),"coba");
        supportPostponeEnterTransition();


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appbar = (AppBarLayout) findViewById(R.id.app_bar_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                if (findViewById(R.id.fragment_container2) == null){
                    if (fm.getBackStackEntryCount() > 1){
                        fm.popBackStack(STACK_RECIPE_DETAIL,0);
                    } else if (fm.getBackStackEntryCount() > 0 ){
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });

        collapsingToolbarLayout.setTitle(recipeName);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView img = (ImageView) findViewById(R.id.image);
        String stringImage = getIntent().getStringExtra("image_cake");

        Picasso.with(this).load("file:///android_asset/Photo_baking/" + stringImage).into(img, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap =  ((BitmapDrawable)img.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Integer primaryDark = getResources().getColor(R.color.colorPrimaryDark);
                        Integer primary = getResources().getColor(R.color.colorPrimary);
                        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
                        supportPostponeEnterTransition();
                    }
                });
            }

            @Override
            public void onError() {
                Log.e("Gagal","Kenapa");

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",recipeName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1 ){
            fm.popBackStack(STACK_RECIPE_DETAIL,0);
            Log.e("Tes1","tes1");
            finish();
        } else if (fm.getBackStackEntryCount() > 0){
            Log.e("Tes2","tes1");
            fm.popBackStack(STACK_RECIPE_DETAIL,0);
        }else {
            Log.e("Tes3","tes1");
            finish();
        }
    }


}
