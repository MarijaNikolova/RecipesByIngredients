package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.adapters.RecipeDetailsIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.IngredientDescription;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Fragment for the recipes details.
 */
public class MyRecipesDetailsFragment extends Fragment {

    private TextView recipeTitleTextView;
    private ImageView recipeImageTextView;
    private TextView recipeSourceTextView;
    private TextView recipeCategoryTextView;
    private TextView recipeYieldTextView;
    private TextView recipeTimeOfCookingTextView;
    private GridView recipeIngredientsGridView;
    private Button goToInstructionsButton;
    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeDetailsView = inflater.inflate(R.layout.my_recipes_recipes_details_layout, container, false);
        getFragmentInnerViewElements(recipeDetailsView);
        getRecipeFromBundle();
        fillRecipeValuesToViewElements();
        addListenerToGoToInstructionsButton();
        return recipeDetailsView;
    }

    private void getFragmentInnerViewElements(View recipeDetailsView) {
        recipeImageTextView = (ImageView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_image);
        recipeTitleTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_title);
        recipeSourceTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_source_url);
        recipeTimeOfCookingTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_time_of_cooking);
        recipeCategoryTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_category);
        recipeYieldTextView = (TextView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_recipe_yield);
        recipeIngredientsGridView = (GridView) recipeDetailsView.findViewById(R.id.my_recipes_recipes_details_ingredients_list);
        goToInstructionsButton = (Button) recipeDetailsView.findViewById(R.id.my_recipe_details_go_to_instructions_button);
    }

    private void getRecipeFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = (Recipe) bundle.getSerializable("recipe");
        }
    }

    private void fillRecipeValuesToViewElements() {
        Log.d("recipeDetails", recipe.toString());
        if (recipe != null) {
            if (recipe.getTitle() != null) {
                recipeTitleTextView.setText(recipe.getTitle());
            }
            if (recipe.getImageUrl() != null) {
                Picasso.with(getActivity()).setLoggingEnabled(true);
                Picasso.with(getActivity()).load(recipe.getImageUrl()).resize(500, 300).into(recipeImageTextView);
            }
            if (recipe.getUrl() != null) {
                recipeSourceTextView.setText(recipe.getUrl());
            }
            if (recipe.getIngredientList() != null) {
                RecipeDetailsIngredientsAdapter recipeDetailsIngredientsAdapter =
                        new RecipeDetailsIngredientsAdapter(getActivity(), recipe.getIngredientList());
                recipeIngredientsGridView.setAdapter(recipeDetailsIngredientsAdapter);
            }
            if (recipe.getTimeOfCooking() != null) {
                recipeTimeOfCookingTextView.setText(recipe.getTimeOfCooking());
                recipeTimeOfCookingTextView.setVisibility(View.VISIBLE);
            }
            if (recipe.getCategory() != null) {
                recipeCategoryTextView.setText(recipe.getCategory());
                recipeCategoryTextView.setVisibility(View.VISIBLE);
            }
            if (recipe.getRecipeYield() != null) {
                recipeYieldTextView.setText(recipe.getRecipeYield());
                recipeYieldTextView.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void addListenerToGoToInstructionsButton() {
      goToInstructionsButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Bundle bundle = new Bundle();
              bundle.putSerializable("recipe", recipe);
              MyRecipesDetailsInstructionsFragment myRecipesDetailsInstructionsFragment =
                      new MyRecipesDetailsInstructionsFragment();
              myRecipesDetailsInstructionsFragment.setArguments(bundle);
              FragmentManager fragmentManager = getFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.content_frame, myRecipesDetailsInstructionsFragment).addToBackStack("details");
              fragmentTransaction.commit();
          }
      });
    }

}
