package com.singletondev.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.singletondev.bakingapp.Adapters.RecipeAdapter;
import com.singletondev.bakingapp.idlingResources.IdlingResources;
import com.singletondev.bakingapp.modelData.resep;
import com.singletondev.bakingapp.network.Endpoints;
import com.singletondev.bakingapp.network.RestAda;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.singletondev.bakingapp.Besa.ALL_RECIPES;

/**
 * Created by Randy Arba on 8/30/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RecipeFragment extends Fragment {
    RecyclerView recyclerView;
    IdlingResources idlingResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);

        initView(view);

        idlingResource = (IdlingResources) ((Besa)getActivity()).getIdleRes();

        idlingResource.setIdleController(false);

        return view;
    }

    public void initView(View view){
        if (view != null){
            recyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler);
        }

        RecipeAdapter recipeAdapter = new RecipeAdapter((Besa)getActivity());
        //jsonPars(recipeAdapter);
        initNetwork(recipeAdapter);

//        Log.e("ViewAttach",view.getTag().toString());
        if (view.getTag() != null && view.getTag().equals("view-land")){
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            RecyclerView.LayoutManager mLinearLayout = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLinearLayout);
        }


    }

//    public void jsonPars(RecipeAdapter recipeAdapter){
//        String json = null;
//        try{
//            InputStream inputStream = getActivity().getResources().getAssets().open("baking.json");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//            json = new String(buffer);
//        } catch (Exception e){
//            Log.e("Gagal",e.getMessage());
//        }
//
//        JsonParser parser = new JsonParser();
//        JsonArray jsonArray = (JsonArray) parser.parse(json);
//        List<resep> list = new ArrayList<>();
//
//        Gson gson = new Gson();
//
//        for (JsonElement jsonArr : jsonArray){
//            list.add(gson.fromJson(jsonArr,resep.class));
//        }
//
//        ArrayList listArrayPort = (ArrayList) list;
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(ALL_RECIPES,listArrayPort);
//
//        if (idlingResource != null){
//            idlingResource.setIdleController(true);
//        }
//        recipeAdapter.setRecipeData(listArrayPort,getContext());
//    }

    public void initNetwork(final RecipeAdapter recipeAdapter){
        Endpoints endpoints = RestAda.getRespond();
        Observable<List<resep>> listObservable = endpoints.getResep();
        listObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<resep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("subcribe",String.valueOf(d.isDisposed()));
                    }

                    @Override
                    public void onNext(List<resep> reseps) {
                        Log.e("hasil",reseps.get(0).getName());
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(ALL_RECIPES,(ArrayList<resep>) reseps);

                        if (idlingResource != null){
                            idlingResource.setIdleController(true);
                        }
                        recipeAdapter.setRecipeData((ArrayList<resep>) reseps,getContext());
                        recyclerView.setAdapter(recipeAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("gagal", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("complete","selesai");

                    }
                });
    }
}
