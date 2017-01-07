package com.recipesbyingredients.com.recipesbyingredients.controllers;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.service.RecipeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Default controller.
 */
@RestController
public class ApplicationController {

    @Autowired
    private RecipeSearchService recipeSearchService;

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
    public List<Recipe> getAllRecipesByTimeOfCooking(@RequestParam("ingredients") List<String> ingredients) {
        List<Recipe> recipeList = null; //recipeSearchService.findByIgredients(ingredients);
        return  recipeList;
    }

}
