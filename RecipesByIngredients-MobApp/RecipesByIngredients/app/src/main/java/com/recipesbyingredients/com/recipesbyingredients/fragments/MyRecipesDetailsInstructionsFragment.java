package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

/**
 * Instructions details fragment.
 */
public class MyRecipesDetailsInstructionsFragment extends Fragment {

    private TextView recipeInstructionsTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeDetailsView =
                inflater.inflate(R.layout.my_recipes_recipes_details_instructions_layout, container, false);
        recipeInstructionsTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_instructions);
        return recipeDetailsView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Recipe recipe = (Recipe) bundle.getSerializable("recipe");
            if (recipe.getStepsForCooking() != null) {
                recipeInstructionsTextView.setMovementMethod(new ScrollingMovementMethod());
                recipeInstructionsTextView.setText(recipe.getStepsForCooking());
            }
        }
    }
}
