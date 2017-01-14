package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipesbyingredients.R;

/**
 * Fragment for the recipes details.
 */
public class MyRecipesDetailsFragment extends Fragment {

    private TextView recipeTitleTextView;
    private ImageView recipeImageTextView;
    private TextView recipeInstructionsTextView;
    private TextView recipeSourceTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeDetailsView = inflater.inflate(R.layout.my_recipes_recipes_details_layout, container, false);

        return recipeDetailsView;
    }
}
