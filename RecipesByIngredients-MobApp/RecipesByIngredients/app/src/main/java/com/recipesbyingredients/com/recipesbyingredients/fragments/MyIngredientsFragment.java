package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.adapters.MyIngredientsIngredientsAdapter;
import com.recipesbyingredients.com.recipesbyingredients.database.SavedIngredientsDataSource;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that represents the my ingredients view.
 */
public class MyIngredientsFragment extends Fragment {

    private GridView ingredientsGridView;
    private Button addIngredientButton;
    private Button deleteIngredientButton;
    private AutoCompleteTextView searchIngredientAutocomplete;
    private SavedIngredientsDataSource savedIngredientsDataSource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myIngredientsView = inflater.inflate(R.layout.my_ingredients_layout, container, false);
        getFragmentInnerViewElements(myIngredientsView);
        fillAutoCompleteTextView();
        return myIngredientsView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateIngredientsTextView();
        addListenerToAddIngredientButton();
    }

    private void getFragmentInnerViewElements(View myIngredientsView) {
        ingredientsGridView = (GridView) myIngredientsView.findViewById(R.id.my_ingredients_list);
        addIngredientButton = (Button) myIngredientsView.findViewById(R.id.my_ingredients_add_ingredient_button);
        deleteIngredientButton = (Button) myIngredientsView.findViewById(R.id.my_ingredients_delete_ingredient_button);
        searchIngredientAutocomplete = (AutoCompleteTextView) myIngredientsView.findViewById(R.id.my_ingredients_add_ingredient_autocomplete_text_view);
    }

    private void populateIngredientsTextView() {

        GetAllSavedIngredientsFromDatabaseAsyncTask getAllSavedIngredientsFromDatabaseAsyncTask =
                new GetAllSavedIngredientsFromDatabaseAsyncTask();
        getAllSavedIngredientsFromDatabaseAsyncTask.execute();
    }

    private void fillAutoCompleteTextView() {

        String [] ingredients = getResources().getStringArray(R.array.ingredients_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        ingredients);
        searchIngredientAutocomplete.setThreshold(0);
        searchIngredientAutocomplete.setAdapter(adapter);
    }

    private void addListenerToAddIngredientButton() {
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = searchIngredientAutocomplete.getText().toString();
                if (!value.equals("") && value.equals(" ") && value != null) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(value);
                    WriteIngredientToDatabaseAsyncTask writeIngredientToDatabaseAsyncTask =
                            new WriteIngredientToDatabaseAsyncTask();
                    writeIngredientToDatabaseAsyncTask.execute(ingredient);
                }
                searchIngredientAutocomplete.setText("");
            }
        });
    }

    private void openDatabaseConnection() throws SQLException {
        savedIngredientsDataSource = new SavedIngredientsDataSource(getActivity());
        savedIngredientsDataSource.open();
    }

    private void closeDatabaseConnection() {
        savedIngredientsDataSource.close();
    }

     private class WriteIngredientToDatabaseAsyncTask extends AsyncTask<Ingredient, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Ingredient... params) {

            Ingredient ingredient = params[0];
            savedIngredientsDataSource.insertIngredient(ingredient);
            return true;
        }

        @Override
        protected void onPreExecute() {
            try {
               openDatabaseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
              closeDatabaseConnection();
            ((BaseAdapter)ingredientsGridView.getAdapter()).notifyDataSetChanged();
            populateIngredientsTextView();

        }
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
        protected void onPostExecute(ArrayList<Ingredient> ingredients) {
           // closeDatabaseConnection();
            setListViewAdapter(ingredients);

        }
    }

    private void setListViewAdapter(ArrayList<Ingredient> ingredients) {
        MyIngredientsIngredientsAdapter myIngredientsIngredientsAdapter =
                new MyIngredientsIngredientsAdapter(getActivity(), ingredients);
        ingredientsGridView.setAdapter(myIngredientsIngredientsAdapter);
    }
}


