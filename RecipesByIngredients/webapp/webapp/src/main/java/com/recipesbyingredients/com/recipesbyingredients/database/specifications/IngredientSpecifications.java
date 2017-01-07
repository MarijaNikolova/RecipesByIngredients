package com.recipesbyingredients.com.recipesbyingredients.database.specifications;

import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Class that represents the specifications for the ingredients.
 */
public final class IngredientSpecifications {

    private IngredientSpecifications() {

    }

    public static Specification<Ingredient> hasName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.<String>get(Ingredient_.name), name);
        };
    }

    public static Specification<Ingredient> haveIngredients(String [] ingredients) {
        return (root, criteriaQuery, criteriaBuilder) -> {



            return null;
        };
    }
}
