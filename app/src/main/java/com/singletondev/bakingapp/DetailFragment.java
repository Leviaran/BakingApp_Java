package com.singletondev.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class DetailFragment2 extends Fragment {
    public List<Steps> mSteps;
    public String mShortDesc;
    public String mDesc;
    public String recipeName;
    public List<resep> mRecipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRecipe = new ArrayList<>();

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES)
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
