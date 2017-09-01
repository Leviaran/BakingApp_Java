package com.singletondev.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.singletondev.bakingapp.Adapters.RecipeAdapter;
import com.singletondev.bakingapp.modelData.resep;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.singletondev.bakingapp.Besa.ALL_RECIPES;

/**
 * Created by Randy Arba on 8/30/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RecipeFragment2 extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initView(View view){
        if (view != null){
            recyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler);
        }

        RecipeAdapter recipeAdapter = new RecipeAdapter((Besa)getActivity());


    }

    public void jsonPars(RecipeAdapter recipeAdapter){
        String json = null;
        try{
            InputStream inputStream = getActivity().getResources().getAssets().open("baking.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer);
        } catch (Exception e){
            Log.e("Gagal",e.getMessage());
        }

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = (JsonArray) parser.parse(json);
        List<resep> list = new ArrayList<>();

        Gson gson = new Gson();

        for (JsonElement jsonArr : jsonArray){
            list.add(gson.fromJson(jsonArr,resep.class));
        }

        ArrayList listArrayPort = (ArrayList) list;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ALL_RECIPES,listArrayPort);
        recipeAdapter.setRecipeData(listArrayPort,getContext());
    }
}
