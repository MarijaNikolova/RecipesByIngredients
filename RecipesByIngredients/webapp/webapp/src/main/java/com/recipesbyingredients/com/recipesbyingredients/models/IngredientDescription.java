package com.recipesbyingredients.com.recipesbyingredients.models;

import javax.persistence.*;

/**
 * Class that represents the model for the ingredients description in the database.
 */
@Entity
@Table(name = "recipeingredient")
public class IngredientDescription {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
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
