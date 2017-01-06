package com.recipesbyingredients.com.recipesbyingredients.database.specifications;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Class that represents the specifications for the recipes class.
 */
public final class RecipeSpecifications {

    private RecipeSpecifications() {

    }

    public static Specification<Recipe> hasCategory(String category) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.<String>get(Recipe_.category), category);
        };
    }
}
