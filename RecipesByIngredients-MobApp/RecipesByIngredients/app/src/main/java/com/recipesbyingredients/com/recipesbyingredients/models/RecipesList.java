package com.recipesbyingredients.com.recipesbyingredients.models;

import java.io.Serializable;
import java.util.List;

/**
 * Recipes list class for json mapping.
 */
public class RecipesList implements Serializable {

    List<Recipe> recipeList;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
