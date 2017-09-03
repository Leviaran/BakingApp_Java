package com.singletondev.bakingapp.modelData;

/**
 * Created by Randy Arba on 8/31/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class resep implements Parcelable {

    private int id;
    private String name;
    private String image_cake;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_cake() {
        return image_cake;
    }

    public void setImage_cake(String image_cake) {
        this.image_cake = image_cake;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image_cake);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    public resep() {
    }

    protected resep(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image_cake = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        this.steps = in.createTypedArrayList(Steps.CREATOR);
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<resep> CREATOR = new Parcelable.Creator<resep>() {
        @Override
        public resep createFromParcel(Parcel source) {
            return new resep(source);
        }

        @Override
        public resep[] newArray(int size) {
            return new resep[size];
        }
    };
}
