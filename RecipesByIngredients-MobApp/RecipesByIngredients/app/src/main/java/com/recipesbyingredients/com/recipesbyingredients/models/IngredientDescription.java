package com.recipesbyingredients.com.recipesbyingredients.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Class that represents the description of ingredients.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class IngredientDescription {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
