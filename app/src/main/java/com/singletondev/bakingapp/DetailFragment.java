package com.singletondev.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.singletondev.bakingapp.Adapters.RecipeDetailAdapter;
import com.singletondev.bakingapp.modelData.Ingredients;
import com.singletondev.bakingapp.modelData.Steps;
import com.singletondev.bakingapp.modelData.resep;
import com.singletondev.bakingapp.widget.updateBakingServ;

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

public class DetailFragment extends Fragment {
    public List<Steps> mSteps;
    public String mShortDesc;
    public String mDesc;
    public String recipeName;
    public List<resep> mRecipe;
    public String image_cake;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRecipe = new ArrayList<>();

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
            image_cake = savedInstanceState.getString("image_cake");
            Log.e("image",image_cake);
        }else {
            mRecipe = getArguments().getParcelableArrayList(SELECTED_RECIPES);
            image_cake = getArguments().getString("image_cake");
            Log.e("image",image_cake);
        }

        List<Ingredients> ingredients = mRecipe.get(0).getIngredients();
        recipeName = mRecipe.get(0).getName();

        View attachView = inflater.inflate(R.layout.fragment_detail,container,false);
        TextView textViewDesc = attachView.findViewById(R.id.recipe_detail_teks);
        RecyclerView recyclerView = attachView.findViewById(R.id.recipe_detail_recycler);

        List<String> recipeInggridientWidget = new ArrayList<>();

        for (Ingredients dataIngridient : ingredients){

            textViewDesc.append("\u2022 "+ dataIngridient.getIngredient()+"\n");
            textViewDesc.append("\t\t\t Quantity: "+ dataIngridient.getQuantity()+"\n");
            textViewDesc.append("\t\t\t Measure: "+ dataIngridient.getMeasure()+"\n\n");

            recipeInggridientWidget.add(
                    dataIngridient.getIngredient()+"\n"+
                    "Quantity: "+ dataIngridient.getQuantity()+"\n"+
                    "Measure: "+ dataIngridient.getMeasure()+"\n");
        }

        RecyclerView.LayoutManager mLinierLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinierLayoutManager);

        RecipeDetailAdapter recyclerViewAdapter = new RecipeDetailAdapter((RecipeDetailACtivity) getActivity(),image_cake);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setRecipeData(mRecipe,getContext());

        updateBakingServ.startService(getContext(), (ArrayList<String>) recipeInggridientWidget,(ArrayList<resep>) mRecipe);

        return attachView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SELECTED_RECIPES,(ArrayList<resep>)mRecipe);
        outState.putString("image_cake",image_cake);
        outState.putString("Title",recipeName);


    }
}
