package model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * All the constants strings are placed in this class.
 */
public class Constants {

    /** String that represents the template url for the recipes from MoiRecepti.mk . **/
    public static final String MOI_RECEPTI_TEMPLATE_URL = "https://moirecepti.mk/category/recepti/page/";
    /** Represents the total number of pages in MoiRecepti.mk site. **/
    public static final int MOI_RECEPTI_TOTAL_NUMBER_OF_PAGES  =  847;
    /** String that represents the name of item prop that contains the category of the recipe. **/
    public static final String MOI_RECEPTI_PREPARATION_INFO_CATEGORY_ITEM_PROP = "recipeCategory";
    /** String that represents the name of item prop that contains the number of persons for which the recipe is prepared. **/
    public static final String MOI_RECEPTI_PREPARATION_INFO_RECIPE_YIELD_ITEM_PROP = "recipeYield";
    /** String that represents the name of item prop that contains the total preparation time of the recipe. **/
    public static final String MOI_RECEPTI_PREPARATION_INFO_TOTAL_TIME_ITEM_PROP = "totalTime";

    //File names
    /** String that represents the name of the file in which all url for the recipes from MoiRecepti are written. **/
    public static final String MOI_RECEPTI_ALL_RECIPES_FILE_NAME = "./resources/moirecepti/moirecepti-allrecipes.txt";
    /** String that represents the name of the file in which all url for the recipes from MoiRecepti are written. **/
    public static final String MOI_RECEPTI_ALL_INGREDIENTS_FILE_NAME = "./resources/moirecepti/moirecepti-allingredients.txt";
    /** String that represents the name of the file in which all url for the recipes from MoiRecepti are written. **/
    public static final String MOI_RECEPTI_CLEANED_INGREDIENTS_FILE_NAME = "./resources/moirecepti/cleanedIngredients.txt";

    //CSS Queries
    /** String that represents the css query that selects the recipes from one page from MoiRecepti. **/
    public static final String MOI_RECEPTI_GET_ALL_RECIPES_FROM_A_PAGE_QUERY = "ul.list-unstyled li.post span.post-image-wrap a";
    /** Css query to find all ingredients for a given recipe from MoiRecepti. **/
    public static final String MOI_RECEPTI_GET_ALL_INGREDIENTS_FOR_A_GIVEN_RECIPE_QUERY = ".column-middle .recipe-meta .recipe-ingredients .panel-body ol li";

}
