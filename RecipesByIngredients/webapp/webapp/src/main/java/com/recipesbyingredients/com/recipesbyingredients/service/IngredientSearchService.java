package com.recipesbyingredients.com.recipesbyingredients.service;

import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

import java.util.List;
import java.util.Set;

/**
 * Service for manipulation with ingredient data.
 */
public interface IngredientSearchService {

    public Set<Recipe> findAllRecipesByIngredientNames(String [] names);

    public List<Ingredient> findAllByName(String name);


}
