package com.singletondev.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singletondev.bakingapp.R;
import com.singletondev.bakingapp.modelData.resep;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randy Arba on 8/31/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder> {

    private ArrayList<resep> mlRecipe;
    private Context context;
    private ListItemClickListener sItemClickListener;
    private List<String> listCake;

    public RecipeAdapter(ListItemClickListener sItemClickListener){
        this.sItemClickListener = sItemClickListener;
    }

    public interface ListItemClickListener{
        void onItemClickListener(resep clickListener, String image_cake);
    }

    public void setRecipeData(ArrayList<resep> recipeIn, Context context){
        this.mlRecipe = recipeIn;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_recipe_items,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        listCake = new ArrayList<>();
        listCake.add("nutella_pie.png");
        listCake.add("brownies.png");
        listCake.add("yellow_cake.png");
        listCake.add("cheese_cake.png");

        String imageURL = "file:///android_asset/Photo_baking/" + listCake.get(position);
        holder.mTextRecyclerView.setText(mlRecipe.get(position).getName());
        holder.mTextItemSteps.setText(String.valueOf(mlRecipe.get(position).getSteps().size()));
        holder.mTextItemServe.setText(String.valueOf(mlRecipe.get(position).getIngredients().size()));

        Picasso.with(context).load(imageURL).into(holder.mimgRecyclerView);

    }

    @Override
    public int getItemCount() {
        return mlRecipe.size();
    }

    protected class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextRecyclerView;
        TextView mTextItemServe;
        TextView mTextItemSteps;
        ImageView mimgRecyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTextRecyclerView = itemView.findViewById(R.id.title);
            mimgRecyclerView = itemView.findViewById(R.id.recipeImage);
            mTextItemServe = itemView.findViewById(R.id.item_serves);
            mTextItemSteps = itemView.findViewById(R.id.item_steps);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Integer clickPosition = getAdapterPosition();
            sItemClickListener.onItemClickListener(mlRecipe.get(clickPosition), listCake.get(clickPosition));

        }
    }
}
