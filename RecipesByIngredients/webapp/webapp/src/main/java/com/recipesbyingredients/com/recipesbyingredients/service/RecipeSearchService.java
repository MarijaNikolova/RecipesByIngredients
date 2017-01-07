package com.recipesbyingredients.com.recipesbyingredients.service;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

import java.util.List;

/**
 * Class that represents the service that searches through the recipes.
 */
public interface RecipeSearchService {

    List<Recipe> findByCategory(String category);

    List<Recipe> findByTimeOfCooking(String timeOfCooking);
}
