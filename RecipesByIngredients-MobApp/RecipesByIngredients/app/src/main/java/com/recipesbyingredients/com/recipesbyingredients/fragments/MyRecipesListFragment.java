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

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that represents the my recipes view.
 */
public class MyRecipesListFragment extends ListFragment {

    private List<Recipe> recipes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipes = getRecipesList();
        List<String> ingredients = new ArrayList<>() ;
        if (getArguments() != null) {
            ingredients = (ArrayList<String>) getArguments().getSerializable("ingredients");
        }


        new GetRecipesFromServerAsyncTask().execute(ingredients);
       // setListAdapter(new MyRecipesRecipesAdapter(getActivity(), recipes));
    }

    // TODO: 1/14/2017 Get recipes from database 
    private List<Recipe> getRecipesList() {
        List<Recipe> recipes = new ArrayList<Recipe>();
        Recipe recipe = new Recipe();
        recipe.setId(12L);
        recipe.setTitle("Prv recept");
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setId(13L);
        recipe.setTitle("Vtor recept");
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setId(12L);
        recipe.setTitle("Tret recept");
        recipe.setImageUrl("https://moirecepti.mk/content/uploads/2016/11/1-2efd7e-800x546.jpg");

        ArrayList<String> list = new ArrayList<String>();
        list.add("100 grama brasno");
        list.add("500 litri voda");
        recipe.setIngredients(list);
        recipes.add(recipe);
        return recipes;
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
            String url = "http://172.20.10.3:8000/getAllRecipesByGivenIngredients?ingredients=јајца,слива";
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            List<Recipe> recipes = restTemplate.getForObject(url, RecipesList.class).getRecipeList();
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);

            setListAdapter(new MyRecipesRecipesAdapter(getActivity(), recipes));
        }
    }
}


