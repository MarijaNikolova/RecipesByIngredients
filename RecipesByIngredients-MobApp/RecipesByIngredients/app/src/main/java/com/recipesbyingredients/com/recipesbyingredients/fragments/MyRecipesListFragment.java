package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.adapters.MyRecipesRecipesAdapter;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.models.RecipesList;
import com.recipesbyingredients.com.recipesbyingredients.utilities.Constants;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Fragment that represents the my recipes view.
 */
public class MyRecipesListFragment extends ListFragment {

    private List<Recipe> recipes;
    private int mode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> ingredients = new ArrayList<>() ;
        if (getArguments() != null) {
            ingredients = (ArrayList<String>) getArguments().getSerializable("ingredients");
            mode = getArguments().getInt("mode");
        }
        setRecipesList(mode, ingredients);
        
    }

    private void setRecipesList(int mode,  List<String> ingredients ) {
        if (mode == Constants.MODE_SEARCHED_RECIPES) {
            getRecipesFromServer(ingredients);
        } else {
            getRecipesFromDatabase();
        }
    }
    
    private List<Recipe> getRecipesFromDatabase() {
        // TODO: 1/21/2017 Add database async task
        return recipes;
    }
    private void getRecipesFromServer(List<String> ingredients) {
        new GetRecipesFromServerAsyncTask().execute(ingredients);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipes.get(position));
        MyRecipesDetailsFragment myRecipesDetailsFragment = new MyRecipesDetailsFragment();
        myRecipesDetailsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,  myRecipesDetailsFragment);
        fragmentTransaction.commit();
    }

    private class GetRecipesFromDatabaseAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(Void... params) {
            return null;
        }
    }

    private class GetRecipesFromServerAsyncTask extends AsyncTask<List<String>, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(List<String>... params) {

            RestTemplate restTemplate = new RestTemplate();
            String url = getUrlWithIngredientsAttached(params[0]);
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Recipe [] recipes = restTemplate.getForObject(url, Recipe[].class);
            List<Recipe> recipesList = Arrays.asList(recipes);
            return recipesList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipesList) {
            super.onPostExecute(recipesList);
            recipes = recipesList;
            setListAdapter(new MyRecipesRecipesAdapter(getActivity(), recipes));
        }

        private String getUrlWithIngredientsAttached(List<String> ingredients) {
            String url = Constants.getRecipesByIngredientsUrl;
            for (String ingredient : ingredients) {
                url += ingredient + ",";
            }
            url = url.substring(0, url.length() - 1);
            return  url;
        }
    }
}


