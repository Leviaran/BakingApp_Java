package com.singletondev.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.singletondev.bakingapp.Adapters.RecipeAdapter;
import com.singletondev.bakingapp.idlingResources.IdlingResources;
import com.singletondev.bakingapp.modelData.resep;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by Randy Arba on 8/29/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class Besa extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {

    @Nullable
    public IdlingResources mIdlingRes;

    public static String ALL_RECIPES = "All_Recipes";
    public static String SELECTED_RECIPES = "Selected_Recipes";
    public static String SELECTED_STEPS = "Selected_Steps";
    public static String SELECTED_INDEX = "Selected_Index";

    @Override
    public void onItemClickListener(resep clickListener, String image_cake) {
        Bundle selectedRecipeBundle = new Bundle();
        ArrayList<resep> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(clickListener);
        selectedRecipeBundle.putParcelableArrayList(SELECTED_RECIPES, selectedRecipe);

        Intent intent = new Intent(this, RecipeDetailACtivity.class);
        intent.putExtras(selectedRecipeBundle);
        intent.putExtra("image_cake", image_cake);
        startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_besa);

        Log.e("Coba","Coba");

        initToolbar();

        getIdleRes();
    }


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdleRes(){
        Log.e("isIdle",String.valueOf(mIdlingRes == null));
        mIdlingRes = new IdlingResources();
        return mIdlingRes;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Baking Apps");
    }

}
