package com.recipesbyingredients.com.recipesbyingredients.service.serviceimpl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.recipesbyingredients.com.recipesbyingredients.database.repository.IngredientRepository;
import com.recipesbyingredients.com.recipesbyingredients.database.specifications.IngredientSpecifications;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.service.IngredientSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Implementation for {@link com.recipesbyingredients.com.recipesbyingredients.service.IngredientSearchService}
 */
@Service
public class IngredientSearchServiceImplementation  implements IngredientSearchService {

    @Autowired
    private IngredientRepository ingredientRepository;


    @Override
    public Set<Recipe> findAllRecipesByIngredientNames(String [] names) {

        TreeSet<Ingredient> ingredients  = new TreeSet<Ingredient>();

        TreeMap<Integer, Set<Recipe>> recipesWithCounters = new TreeMap<Integer, Set<Recipe>>();
        for (String s : names) {
            Specification<Ingredient> ingredientSpecification = IngredientSpecifications.hasName(s);
            Ingredient ingredient = ingredientRepository.findOne(ingredientSpecification);
            ingredients.add(ingredient);
            Set<Recipe> recipes = ingredient.getRecipes();
            recipesWithCounters.put(recipes.size(), recipes);
        }

        Set<Recipe> recipes =  intersectionOfSets(recipesWithCounters);
        Set<Recipe> recipesSubset = ImmutableSet.copyOf(Iterables.limit(recipes, 10));
        return recipesSubset;
    }

    @Override
    public List<Ingredient> findAllByName(String name) {

        Specification<Ingredient> ingredientSpecification = IngredientSpecifications.hasName(name);
        List<Ingredient> ingredients = ingredientRepository.findAll(ingredientSpecification);
        return ingredients;
    }

    private Set<Recipe> intersectionOfSets(TreeMap<Integer, Set<Recipe>> allRecipesSet) {

        Set<Recipe> recipesIntersection = allRecipesSet.firstEntry().getValue();
        Iterator<Map.Entry<Integer, Set<Recipe>>> iterator = allRecipesSet.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Set<Recipe>> entry = iterator.next();
            Set<Recipe> recipes = entry.getValue();
            recipes.retainAll(recipesIntersection);
            recipesIntersection = recipes;
        }
        return recipesIntersection;

    }

}
