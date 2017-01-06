package com.recipesbyingredients.com.recipesbyingredients.service.serviceimpl;

import com.recipesbyingredients.com.recipesbyingredients.database.repository.RecipeRepository;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.service.RecipeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.recipesbyingredients.com.recipesbyingredients.database.specifications.RecipeSpecifications;
import java.util.List;

/**
 * Implementation for {@link com.recipesbyingredients.com.recipesbyingredients.service.RecipeSearchService}.
 */
@Service
public class RecipeSearchServiceImplementation implements RecipeSearchService{

    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    @Override
    public List<Recipe> findByCategory(String category) {
        Specification<Recipe> specification = RecipeSpecifications.hasCategory(category);
        List<Recipe> recipes = recipeRepository.findAll(specification);
        return recipes;
    }
}
