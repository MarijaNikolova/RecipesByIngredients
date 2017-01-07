package com.recipesbyingredients.com.recipesbyingredients.controllers;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.service.IngredientSearchService;
import com.recipesbyingredients.com.recipesbyingredients.service.RecipeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Default controller.
 */
@RestController
public class ApplicationController {

    @Autowired
    private RecipeSearchService recipeSearchService;

    @Autowired
    private IngredientSearchService ingredientSearchService;

    @RequestMapping("/")
    public String sayHello() {

        String intro = "This is a web service for the recipes and igredients";
        return intro;
    }

    @RequestMapping(value = "/getAllRecipesByCategory", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Recipe> getAllRecipesByCategory(@RequestParam("category") String category) {
        List<Recipe> recipeList = recipeSearchService.findByCategory(category);
        return  recipeList;
    }

    @RequestMapping(value = "/getAllRecipesByTimeOfCooking", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Recipe> getAllRecipesByTimeOfCooking(@RequestParam("timeOfCooking") String timeOfCooking) {
        List<Recipe> recipeList = recipeSearchService.findByTimeOfCooking(timeOfCooking);
        return  recipeList;
    }

    @RequestMapping(value = "/getAllRecipesByGivenIngredients", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Set<Recipe> getAllRecipesByTimeOfCooking(@RequestParam("ingredients") String [] ingredients) {
        Set<Recipe> recipe = ingredientSearchService.findAllRecipesByIngredientNames(ingredients); //recipeSearchService.findByIgredients(ingredients);
        return  recipe;
    }

}
