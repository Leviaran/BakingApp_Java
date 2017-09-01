package com.singletondev.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.singletondev.bakingapp.R;

/**
 * Created by Randy Arba on 8/31/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RecipeAdapter2 {

    protected class RecyclerViewHolder extends RecyclerView.ViewHolder, View.OnClickListener{

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
            
        }
    }
}
