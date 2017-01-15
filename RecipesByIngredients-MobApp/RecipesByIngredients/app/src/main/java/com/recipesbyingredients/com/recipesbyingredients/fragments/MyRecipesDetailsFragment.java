package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.squareup.picasso.Picasso;

/**
 * Fragment for the recipes details.
 */
public class MyRecipesDetailsFragment extends Fragment {

    private TextView recipeTitleTextView;
    private ImageView recipeImageTextView;
    private TextView recipeInstructionsTextView;
    private TextView recipeSourceTextView;
    private GridView recipeIngredientsGridView;
    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeDetailsView = inflater.inflate(R.layout.my_recipes_recipes_details_layout, container, false);
        getFragmentInnerViewElements(recipeDetailsView);
        getRecipeFromBundle();
        fillRecipeValuesToViewElements();
        return recipeDetailsView;
    }

    private void getFragmentInnerViewElements(View recipeDetailsView) {
        recipeImageTextView = (ImageView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_image);
        recipeTitleTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_title);
        recipeInstructionsTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_instructions);
        recipeSourceTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_source_url);
        recipeIngredientsGridView = (GridView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_ingredients_list);
        recipeInstructionsTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_instructions);

    }

    private void getRecipeFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = (Recipe) bundle.getSerializable("recipe");
        }
    }

    private void fillRecipeValuesToViewElements() {
        if (recipe != null) {
            if (recipe.getTitle() != null) {
                recipeTitleTextView.setText(recipe.getTitle());
            }
            if (recipe.getImageUrl() != null) {
                Picasso.with(getActivity()).setLoggingEnabled(true);
                Picasso.with(getActivity()).load(recipe.getImageUrl()).resize(100, 100).into(recipeImageTextView);
            }
            if (recipe.getUrl() != null) {
                recipeSourceTextView.setText(recipe.getUrl());
            }
            if (recipe.getIngredients() != null) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recipe.getIngredients());
                recipeIngredientsGridView.setAdapter(arrayAdapter);
            }

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
