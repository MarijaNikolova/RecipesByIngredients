package com.recipesbyingredients.com.recipesbyingredients.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Class that represents the Recipe view model.
 */
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable, Comparable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private long id;
    @Column(name = "title", nullable = true, unique = false)
    private String title;
    @Column(name = "url", nullable = true, unique = false)
    private String url;
    @Column(name = "category", nullable = true, unique = false)
    private String category;
    @Column(name = "time_of_cooking", nullable = true, unique = false)
    private String timeOfCooking;
    @Column(name = "steps_for_cooking", nullable = true, unique = false, columnDefinition = "TEXT")
    private String stepsForCooking;
    @Column(name = "recipe_yield", nullable = true, unique = false)
    private String recipeYield;
    @Column(name = "creation_date", nullable = true, unique = false)
    private Date creationDate;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<IngredientDescription> ingredientList;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingredient_inverted_index", joinColumns = {
            @JoinColumn(name = "id_recipe", nullable = true, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_ingredient", nullable = true, updatable = false)})
    private Set<Ingredient> ingredients;

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

    @Override
    public String toString() {
        return this.title + "\n";
    }

    @Override
    public int compareTo(Object o) {
        return Long.compare(this.id,((Recipe)o).getId());
    }
}
