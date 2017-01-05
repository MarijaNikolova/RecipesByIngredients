package com.recipesbyingredients.com.recipesbyingredients.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Class that represents the Recipe view model.
 */
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private long id;
    @Column(name = "title", nullable = true, unique = false)
    private String title;
    @Column(name = "url", nullable = true, unique = false)
    private String url;
    @Column(name = "category", nullable = true, unique = false)
    private String category;
    @Column(name = "timeOfCooking", nullable = true, unique = false)
    private String timeOfCooking;
    @Column(name = "stepsForCooking", nullable = true, unique = false, columnDefinition = "TEXT")
    private String stepsForCooking;
    @Column(name = "recipeYield", nullable = true, unique = false)
    private String recipeYield;
    @Column(name = "creationDate", nullable = true, unique = false)
    @Type(type = "date")
    private Date creationDate;
    @Column(name = "imageUrl", nullable = true, unique = false)
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe",fetch = FetchType.EAGER)
    private List<IngredientDescription> ingredientList;

    public Recipe() {

    }

    public Recipe(String title, String url, String category, String timeOfCooking) {
        this.title = title;
        this.url = url;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<IngredientDescription> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientDescription> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
