package model;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Class that represents the recipes.
 */
public class Recipe {

    private long id;
    private String title;
    private String url;
    private String category;
    private String timeOfCooking;
    private String stepsForCooking;
    private String recipeYield;
    private Date creationDate;
    private Set<Ingredient> ingredientList;

    public Recipe() {

    }

    public Recipe(String title, String url, String category, String timeOfCooking, Set<Ingredient> ingredientList) {
        this.title = title;
        this.url = url;
        this.category = category;
        this.timeOfCooking = timeOfCooking;
        this.ingredientList = ingredientList;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Set<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getTimeOfCooking() {
        return timeOfCooking;
    }

    public void setTimeOfCooking(String timeOfCooking) {
        this.timeOfCooking = timeOfCooking;
    }

    public String getStepsForCooking() {
        return stepsForCooking;
    }

    public void setStepsForCooking(String stepsForCooking) {
        this.stepsForCooking = stepsForCooking;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
