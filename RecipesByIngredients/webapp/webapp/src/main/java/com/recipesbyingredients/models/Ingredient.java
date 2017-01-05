package com.recipesbyingredients.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Class that represents the ingredients.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "name", nullable = true, unique = false, length = 255)
    private String name;
    @Column(name = "description", nullable = true, unique = false, length = 255)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingredientinvertedindex", joinColumns = {
            @JoinColumn(name = "id_ingredient", nullable = true, updatable = false)},
    inverseJoinColumns = {
            @JoinColumn(name = "id_recipe", nullable = true, updatable = false)})
    private Set<Recipe> recipes;

    public Ingredient() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
