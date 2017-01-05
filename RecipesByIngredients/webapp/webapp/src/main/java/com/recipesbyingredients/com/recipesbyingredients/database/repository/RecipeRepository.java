package com.recipesbyingredients.com.recipesbyingredients.database.repository;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

/**
 * Repository for the recipes.
 */
public interface RecipeRepository extends Repository<Recipe, Long>, JpaSpecificationExecutor<Recipe>{
}
