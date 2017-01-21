package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.adapters.MyIngredientsIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.adapters.SearchRecipesIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.database.SavedIngredientsDataSource;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.utilities.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that represents the search view.
 */
public class SearchRecipesFragment extends Fragment {

    private GridView ingredientsGridView;
    private Button addIngredientButton;
    private Button searchRecipesButton;
    private AutoCompleteTextView addRecipeAutoCompleteTextView;
    private ArrayList<Ingredient> ingredients;
    private SavedIngredientsDataSource savedIngredientsDataSource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchRecipesView = inflater.inflate(R.layout.search_layout, container, false);
        getFragmentInnerViewElements(searchRecipesView);
        fillAutoCompleteTextView();
        addListenerToAddIngredientButton();
        addListenerSearchRecipesButton();
        getIngredientsFromDatabase();
        return searchRecipesView;
    }

    private void getFragmentInnerViewElements(View myIngredientsView) {
        ingredientsGridView = (GridView) myIngredientsView.findViewById(R.id.search_layout_ingredients_list);
        addIngredientButton = (Button) myIngredientsView.findViewById(R.id.search_layout_add_ingredient_button);
        searchRecipesButton = (Button) myIngredientsView.findViewById(R.id.search_layout_search_button);
        addRecipeAutoCompleteTextView = (AutoCompleteTextView) myIngredientsView.findViewById(R.id.search_layout_add_ingredient_autocomplete_text_view);
    }

    private void setListViewAdapter() {
        SearchRecipesIngredientsAdapter searchRecipesIngredientsAdapter =
                new SearchRecipesIngredientsAdapter(getActivity(), ingredients);
        ingredientsGridView.setAdapter(searchRecipesIngredientsAdapter);
        Log.d("ingredientsList", ingredients.toString());
    }

    private void getIngredientsFromDatabase() {
        new GetAllSavedIngredientsFromDatabaseAsyncTask().execute();
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

    private void addListenerToAddIngredientButton() {
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = addRecipeAutoCompleteTextView.getText().toString();
                if (!value.equals("") && !value.equals(" ") && !ingredients.toString().contains(value)) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(value);
                    ingredient.setIsChecked(true);
                    Log.d("ingredientsList", ingredients.toString());
                    if (!ingredients.contains(ingredient)) {
                        ingredients.add(ingredient);
                        ((BaseAdapter) ingredientsGridView.getAdapter()).notifyDataSetChanged();
                        addRecipeAutoCompleteTextView.setText("");
                    }
                }

            }
        });
    }


    private void addListenerSearchRecipesButton() {
        searchRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> checkedIngredients = getCheckedIngredients();
                openRecipesListFragment(checkedIngredients);
            }
        });
    }

    private ArrayList<String> getCheckedIngredients() {
        ArrayList<String> checkedIngredients = new ArrayList<String>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.isChecked()) {
                checkedIngredients.add(ingredient.getName());
            }
        }
        return checkedIngredients;
    }

    private void openRecipesListFragment(ArrayList<String> checkedIngredients) {
        MyRecipesListFragment myRecipesListFragment = new MyRecipesListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredients", checkedIngredients);
        bundle.putInt("mode", Constants.MODE_SEARCHED_RECIPES);
        myRecipesListFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, myRecipesListFragment);
        fragmentTransaction.commit();
    }

    private class GetAllSavedIngredientsFromDatabaseAsyncTask extends AsyncTask<Void, Void, ArrayList<Ingredient>> {

        @Override
        protected void onPreExecute() {
            try {
                openDatabaseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected ArrayList<Ingredient> doInBackground(Void... params) {
            ArrayList<Ingredient> ingredients = savedIngredientsDataSource.getAllIngredients();
            return ingredients;
        }

        @Override
        protected void onPostExecute(ArrayList<Ingredient> ingredientsList) {
            ingredients = ingredientsList;
            setAllIngredientsToBeChecked(ingredients);
            setListViewAdapter();
            closeDatabaseConnection();
        }
    }

    private void setAllIngredientsToBeChecked(ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            ingredient.setIsChecked(true);
        }
    }

    private void openDatabaseConnection() throws SQLException {
        savedIngredientsDataSource = new SavedIngredientsDataSource(getActivity());
        savedIngredientsDataSource.open();
    }

    private void closeDatabaseConnection() {
        savedIngredientsDataSource.close();
    }
}


