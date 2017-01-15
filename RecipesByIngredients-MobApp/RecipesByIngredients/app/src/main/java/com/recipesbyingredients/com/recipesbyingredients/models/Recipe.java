package com.recipesbyingredients.com.recipesbyingredients.models;

import java.io.Serializable;
import java.util.List;

/**
 * Model for recipes.
 */
public class Recipe implements Serializable {

    private long id;
    private String title;
    private String url;
    private List<String> ingredients;
    private String timeOfCooking;
    private String recipeYield;
    private String imageUrl;
    private String category;
    private String instructions;
    private String ingredientsString;

    public Recipe() {

    }

    public Recipe(long id, String category, String imageUrl, String recipeYield, List<String> ingredients, String title, String url, String timeOfCooking) {
        this.id = id;
        this.category = category;
        this.imageUrl = imageUrl;
        this.recipeYield = recipeYield;
        this.ingredients = ingredients;
        this.title = title;
        this.url = url;
        this.timeOfCooking = timeOfCooking;
    }

    public Recipe(long id, String ingredientsString, String instructions, String category, String imageUrl, String recipeYield, String timeOfCooking, String url, String title) {
        this.id = id;
        this.ingredientsString = ingredientsString;
        this.instructions = instructions;
        this.category = category;
        this.imageUrl = imageUrl;
        this.recipeYield = recipeYield;
        this.timeOfCooking = timeOfCooking;
        this.url = url;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
    }

    public String getTimeOfCooking() {
        return timeOfCooking;
    }

    public void setTimeOfCooking(String timeOfCooking) {
        this.timeOfCooking = timeOfCooking;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredientsString() {
        return ingredientsString;
    }

    public void setIngredientsString(String ingredientsString) {
        this.ingredientsString = ingredientsString;
    }
}
