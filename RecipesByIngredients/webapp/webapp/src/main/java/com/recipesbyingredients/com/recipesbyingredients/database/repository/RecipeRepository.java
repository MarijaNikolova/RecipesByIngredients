package com.recipesbyingredients.com.recipesbyingredients.database.repository;

import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository for the recipes.
 */
public interface RecipeRepository extends Repository<Recipe, Long>, JpaSpecificationExecutor<Recipe>{

    List<Recipe> findAllByCategory(String category);

    List<Recipe> findAllByRecipeYield(String recipeYield);

    List<Recipe> findAllByTimeOfCooking(String timeOfCooking);

}
