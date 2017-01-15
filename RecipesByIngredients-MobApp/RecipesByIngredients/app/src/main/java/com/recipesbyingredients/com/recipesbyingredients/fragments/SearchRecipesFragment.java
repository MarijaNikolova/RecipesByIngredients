package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.adapters.MyIngredientsIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.adapters.SearchRecipesIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;

import java.util.ArrayList;

/**
 * Fragment that represents the search view.
 */
public class SearchRecipesFragment extends Fragment {

    private GridView ingredientsGridView;
    private Button addIngredientButton;
    private Button searchRecipesButton;
    private AutoCompleteTextView addRecipeAutoCompleteTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchRecipesView = inflater.inflate(R.layout.search_layout, container, false);
        getFragmentInnerViewElements(searchRecipesView);
        setListViewAdapter();
        fillAutoCompleteTextView();
        return searchRecipesView;
    }

    private void getFragmentInnerViewElements(View myIngredientsView) {
        ingredientsGridView = (GridView) myIngredientsView.findViewById(R.id.search_layout_ingredients_list);
        addIngredientButton = (Button) myIngredientsView.findViewById(R.id.search_layout_add_ingredient_button);
        searchRecipesButton = (Button) myIngredientsView.findViewById(R.id.search_layout_search_button);
        addRecipeAutoCompleteTextView = (AutoCompleteTextView) myIngredientsView.findViewById(R.id.search_layout_add_ingredient_autocomplete_text_view);
    }

    private void setListViewAdapter() {
        ArrayList<Ingredient> ingredients = getIngredients();
        SearchRecipesIngredientsAdapter searchRecipesIngredientsAdapter =
                new SearchRecipesIngredientsAdapter(getActivity(), ingredients);
        ingredientsGridView.setAdapter(searchRecipesIngredientsAdapter);
        Log.d("ingredientsList", ingredients.toString());
    }

    private ArrayList<Ingredient> getIngredients() {

        // TODO: 1/14/2017 This should be replaced with taking ingredients from database.
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("jajca");
        ingredients.add(ingredient);
        ingredient = new Ingredient();
        ingredient.setId(2);
        ingredient.setName("авокадо");
        ingredient.setIsChecked(true);
        ingredients.add(ingredient);
        ingredient = new Ingredient();
        ingredient.setId(3);
        ingredient.setName("brasno");
        ingredients.add(ingredient);
        ingredient = new Ingredient();
        ingredient.setId(4);
        ingredient.setName("sliva");
        ingredients.add(ingredient);
        return ingredients;
    }

    private void fillAutoCompleteTextView() {

        String [] ingredients = getResources().getStringArray(R.array.ingredients_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        ingredients);
        addRecipeAutoCompleteTextView.setThreshold(0);
        addRecipeAutoCompleteTextView.setAdapter(adapter);
    }
}


