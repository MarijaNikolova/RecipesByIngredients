package com.recipesbyingredients.com.recipesbyingredients.models;

import sun.reflect.generics.tree.Tree;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class that represents the ingredients.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable, Comparable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "name", nullable = true, unique = false, length = 255)
    private String name;
    @Column(name = "description", nullable = true, unique = false, length = 255)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingredient_inverted_index", joinColumns = {
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

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Ingredient)o).getName());
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }


}
