package com.singletondev.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singletondev.bakingapp.R;
import com.singletondev.bakingapp.modelData.Steps;
import com.singletondev.bakingapp.modelData.resep;
import com.singletondev.bakingapp.network.VideoRequestHandler;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

/**
 * Created by Randy Arba on 8/31/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecyclerViewHolderDetail>{

    private List<Steps> mSteps;
    String mSortDesc;
    String mDesc;
    private String recipeName;
    private Context context;
    private List<resep> reseps;
    private ListItemClickListerer listItem;
    private String image_cake;
    VideoRequestHandler videoRequestHandler;
    Picasso picassoInstance;

    public RecipeDetailAdapter(ListItemClickListerer listItemClickListerer, String image_cake){
        this.listItem = listItemClickListerer;
        this.image_cake = image_cake;
    }


    public interface ListItemClickListerer{
        void onListItemClick(List<Steps> steps, int clickItemIndex, String recipeName);
    }

    public void setRecipeData(List<resep> steps, Context context){
        this.mSteps = steps.get(0).getSteps();
        this.recipeName = steps.get(0).getName();
        this.context = context;
        this.reseps = steps;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerViewHolderDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carview_recipe_items_detail,parent,false);
        RecyclerViewHolderDetail recyclerViewHolderDetail = new RecyclerViewHolderDetail(view);


        return recyclerViewHolderDetail;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderDetail holder, int position) {

        videoRequestHandler = new VideoRequestHandler();
        picassoInstance = new Picasso.Builder(context)
                .addRequestHandler(videoRequestHandler)
                .build();


        String imageURL = "file:///android_asset/Photo_baking/" + image_cake;
        Log.e("image",imageURL);
        holder.textShortDesc.setText(mSteps.get(position).getShortDescription());
        holder.textDesc.setText(mSteps.get(position).getDescription());

        String jsonURL = mSteps.get(position).getThumbnailURL();

        if (!jsonURL.equals("")){
            Log.e("SON",jsonURL);
            Picasso.with(context).load(jsonURL).into(holder.imageThumbnail);
//          picassoInstance.load("video"+":"+jsonURL).into(holder.imageThumbnail);

        } else {
            Picasso.with(context).load(imageURL).into(holder.imageThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    protected class RecyclerViewHolderDetail extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageThumbnail;
        TextView textShortDesc;
        TextView textDesc;

        public RecyclerViewHolderDetail(View itemView) {
            super(itemView);
            imageThumbnail = itemView.findViewById(R.id.recipeImage);
            textShortDesc = itemView.findViewById(R.id.shor_description_text);
            textDesc = itemView.findViewById(R.id.description_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Integer clickPosition = getAdapterPosition();
            listItem.onListItemClick(mSteps,clickPosition,recipeName);


        }
    }
}
