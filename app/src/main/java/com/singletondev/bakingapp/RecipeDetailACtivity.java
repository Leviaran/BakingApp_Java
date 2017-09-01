package com.singletondev.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.singletondev.bakingapp.Adapters.RecipeDetailAdapter;
import com.singletondev.bakingapp.modelData.Steps;
import com.singletondev.bakingapp.modelData.resep;

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

public class RecipeDetailACtivity2 extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListerer {

    List<resep> recipe;
    String recipeName;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appbar;
    Boolean flag;

    @Override
    public void onListItemClick(List<Steps> step, int clickItemIndex, String recipeName) {

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

            DetailFragment fragmentDetail = new DetailFragment();
            fragmentDetail.setArguments(selectedBundle);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragment_container,fragmentDetail)
                    .addToBackStack(STACK_RECIPE_DETAIL)
        }
    }
}
