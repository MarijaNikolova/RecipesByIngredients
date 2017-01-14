package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.adapters.MyIngredientsIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;

import java.util.ArrayList;

/**
 * Fragment that represents the my ingredients view.
 */
public class MyIngredientsFragment extends Fragment {

    private ListView ingredientsListView;
    private Button addIngredientButton;
    private Button deleteIngredientButton;
    private AutoCompleteTextView searchIngredientAutocomplete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myIngredientsView = inflater.inflate(R.layout.my_ingredients_layout, container, false);
        getFragmentInnerViewElements(myIngredientsView);
        setListViewAdapter();
        return myIngredientsView;
    }

    private void getFragmentInnerViewElements(View myIngredientsView) {
        ingredientsListView = (ListView) myIngredientsView.findViewById(R.id.my_ingredients_list);
        addIngredientButton = (Button) myIngredientsView.findViewById(R.id.my_ingredients_add_ingredient_button);
        deleteIngredientButton = (Button) myIngredientsView.findViewById(R.id.my_ingredients_delete_ingredient_button);
        searchIngredientAutocomplete = (AutoCompleteTextView) myIngredientsView.findViewById(R.id.my_ingredients_add_ingredient_autocomplete_text_view);
    }

    private void setListViewAdapter() {
        ArrayList<Ingredient> ingredients = getIngredients();
        MyIngredientsIngredientsAdapter myIngredientsIngredientsAdapter =
                new MyIngredientsIngredientsAdapter(getActivity(), ingredients);
        ingredientsListView.setAdapter(myIngredientsIngredientsAdapter);
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
}


