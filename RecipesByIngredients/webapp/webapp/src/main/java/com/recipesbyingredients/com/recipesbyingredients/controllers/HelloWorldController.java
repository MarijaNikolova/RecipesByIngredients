package com.recipesbyingredients.com.recipesbyingredients.controllers;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;
import com.recipesbyingredients.com.recipesbyingredients.service.RecipeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Default controller.
 */
@RestController
public class HelloWorldController {

    @Autowired
    private RecipeSearchService recipeSearchService;

    @RequestMapping("/")
    public String sayHello() {

        List<Recipe> recipeList = recipeSearchService.findByCategory("Лесно");
        return recipeList.toString();
    }
}
