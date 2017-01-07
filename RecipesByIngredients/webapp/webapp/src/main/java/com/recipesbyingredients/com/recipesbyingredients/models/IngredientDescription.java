package com.recipesbyingredients.com.recipesbyingredients.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class that represents the model for the ingredients description in the database.
 */
@Entity
@Table(name = "recipe_ingredient")
public class IngredientDescription implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "ingredient_description", nullable = true, unique = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "id_recipe")
    private Recipe recipe;

    public IngredientDescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
