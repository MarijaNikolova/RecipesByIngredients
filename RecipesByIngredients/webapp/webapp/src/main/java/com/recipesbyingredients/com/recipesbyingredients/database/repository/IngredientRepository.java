package com.recipesbyingredients.com.recipesbyingredients.database.repository;

import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repository for the ingredient class.
 */
public interface IngredientRepository extends Repository<Long, Ingredient>, JpaSpecificationExecutor<Ingredient>{

    public Set<Ingredient> findAllByName(String name);

    public Ingredient findById(Long id);

}
