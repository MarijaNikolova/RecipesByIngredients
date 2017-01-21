package com.recipesbyingredients.com.recipesbyingredients.utilities;

/**
 * Contains some constants which will not be taken from strings.
 */
public class Constants {

    public static final int MODE_SEARCHED_RECIPES = 0;
    public static final int MODE_SAVED_RECIPES = 1;

    public static final String getRecipesByIngredientsUrl =
            "http://172.20.10.3:8000/getAllRecipesByGivenIngredients?ingredients=";
}
