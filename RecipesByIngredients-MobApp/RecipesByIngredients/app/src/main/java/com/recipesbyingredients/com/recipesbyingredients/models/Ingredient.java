package com.recipesbyingredients.com.recipesbyingredients.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Ingredient model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient implements Serializable{

    private long id;
    private String name;
    private boolean isChecked;

    public Ingredient() {
    }

    public Ingredient(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
