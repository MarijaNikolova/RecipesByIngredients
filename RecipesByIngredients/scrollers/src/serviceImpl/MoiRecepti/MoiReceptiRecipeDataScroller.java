package serviceImpl.MoiRecepti;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import model.Constants;
import model.Ingredient;
import model.Recipe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class that represents the scroller for the ingredients and the preparation for the recipes.
 */
public class MoiReceptiRecipeDataScroller {

    private PrintWriter printWriterForRecipesData;
    private TreeMap<String, List<String>> allIngredients;
    private TreeMap<String, Set<Long>> ingredientsContainedInRecipes;

    public MoiReceptiRecipeDataScroller(PrintWriter printWriterForRecipesData,
                                        TreeMap<String, List<String>> allIngredients) throws IOException {

        this.printWriterForRecipesData = printWriterForRecipesData;
        this.allIngredients = allIngredients;
        this.ingredientsContainedInRecipes = new TreeMap<String, Set<Long>>();
    }


    public void scrollDocumentForIngredientsAndCreateInvertedIndexFile(Recipe recipe, Document document) throws IOException {

        Set<Ingredient> ingredients = getIngredients(document);
        recipe.setIngredientList(ingredients);
        addRecipesToInvertedIndex(recipe.getId(), ingredients);
       // System.out.println(this.ingredientsContainedInRecipes.toString());
    }

    public void scrollDocumentForRecipeInfo(Recipe recipe, Document document) throws IOException {

        getCreationDate(recipe, document);
        getTitle(recipe, document);
        getPreparationInfo(recipe, document);
        Set<Ingredient> ingredients = getIngredientsWithDescription(document);
        recipe.setIngredientList(ingredients);
        getImageUrl(recipe, document);
        getPreparationSteps(recipe, document);


    }


    public void scrollDocumentAndWriteToFile() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.MOI_RECEPTI_ALL_RECIPES_FILE_NAME));
        String line = "";
        int counter = 0;
        try {
            while ((line = bufferedReader.readLine()) != null) {

                String recipeUrl = line;
                Document document = Jsoup.connect(recipeUrl).timeout(100*1000).get();
                Recipe recipe = new Recipe();
                recipe.setUrl(recipeUrl);
                recipe.setId(counter);
                //scrollDocumentForIngredientsAndCreateInvertedIndexFile(recipe, document);
                scrollDocumentForRecipeInfo(recipe, document);
                writeRecipeInfoToFile(recipe);
                System.out.println(counter);
                ++counter;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getCreationDate(Recipe recipe, Document document) {

        Elements creationDateElement =
                document.select(".column-left .post .main-image .post-date");

        String creationDateString = creationDateElement.attr("content");

        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

        try {
            if (!Strings.isNullOrEmpty(creationDateString)) {
                Date creationDate = simpleDateFormat.parse(creationDateString);
                recipe.setCreationDate(creationDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void getTitle(Recipe recipe, Document document) {

        Elements titleElement =
                document.select(".column-left .post .post-title a");

        String recipeTitle = titleElement.text();
        recipe.setTitle(recipeTitle);

    }

    private void getImageUrl(Recipe recipe, Document document) {
        Elements imageElement =
                document.select(".column-left .post .post-image img");
        String imageSource = imageElement.attr("src");
        recipe.setPictureUrl(imageSource);
    }

    private void getPreparationSteps(Recipe recipe, Document document) {

        Elements instructionsElement =
                document.select(".column-left .post .post-content p");
        String instructions = instructionsElement.text();
       // System.out.println(instructions);
        recipe.setStepsForCooking(instructions);

    }



    private void getPreparationInfo(Recipe recipe, Document document) {

        Elements preparationInfoElements = document.select(".column-middle .recipe-meta .prep-info span");

        for (Element element : preparationInfoElements) {

            String itemprop = element.attr("itemprop");

            if (Constants.MOI_RECEPTI_PREPARATION_INFO_CATEGORY_ITEM_PROP.equals(itemprop)) {
                String category = element.text();
                recipe.setCategory(category);
            } else if(Constants.MOI_RECEPTI_PREPARATION_INFO_RECIPE_YIELD_ITEM_PROP.equals(itemprop)) {
                String recipeYield = element.text();
                recipe.setRecipeYield(recipeYield);
            } else if(Constants.MOI_RECEPTI_PREPARATION_INFO_TOTAL_TIME_ITEM_PROP.equals(itemprop)) {
                String totalTime = element.text();
                recipe.setTimeOfCooking(totalTime);
            }
        }

    }

    private Set<Ingredient> getIngredients(Document document) {

        TreeSet<Ingredient> ingredients = new TreeSet<Ingredient>();

        Elements ingredientsList =
                document.select(".column-middle .recipe-meta .recipe-ingredients .panel-body ol li");

        for (Element ingredientElement : ingredientsList) {

            String ingredientText = ingredientElement.text();
            TreeSet<String> mappedStrings = mapIngredientToCleanedIngredients(ingredientText);
            ingredients.addAll(getIngredientsList(mappedStrings));
        }
        //System.out.println(ingredients.size() + " " + ingredients.toString());
        return ingredients;

    }

    private Set<Ingredient> getIngredientsWithDescription(Document document) {

        HashSet<Ingredient> ingredients = new HashSet<Ingredient>();

        Elements ingredientsList =
                document.select(".column-middle .recipe-meta .recipe-ingredients .panel-body ol li");

        for (Element ingredientElement : ingredientsList) {

            String ingredientText = ingredientElement.text();
            Ingredient ingredient = new Ingredient();
            ingredient.setDescription(ingredientText);
            ingredients.add(ingredient);
        }
        //System.out.println(ingredients.size() + " " + ingredients.toString());
        return ingredients;

    }

    private TreeSet<String>  mapIngredientToCleanedIngredients(String ingredientText) {

        TreeSet<String> ingredientsSet = new TreeSet<String>();
        Iterator<Map.Entry<String, List<String>>> iterator = allIngredients.entrySet().iterator();
        List<String> subjectsAndUnmodifiedWords = getSubjectsAndUnmodifiedWords();
       // System.out.println(ingredientText);
        while(iterator.hasNext()){
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();
            List<String> entryValues = entry.getValue();
            ingredientText =  ingredientText.replaceAll("[(\\-)(\\/)(\\.)(\\()(\\))(,)]"," ");
            String [] splittedIngredient = ingredientText.split(" ");

            for (String s : splittedIngredient) {

                if (entryValues != null && (entryValues.contains(s)) && !subjectsAndUnmodifiedWords.contains(s)) {
                    ingredientsSet.add(key);
                   // System.out.println(s);
                } else if((key.contains(s) || key.equals(s)) && !subjectsAndUnmodifiedWords.contains(s)) {
                    ingredientsSet.add(key);
                   // System.out.println(s);
                }

            }
            //System.out.println();
        }
       //System.out.println(ingredientsSet.size() + " " + ingredientsSet.toString());


        return ingredientsSet;
    }


    private List<Ingredient> getIngredientsList(TreeSet<String> ingredientsSet) {

        List<Ingredient> ingredientList = Lists.newArrayList();
        Iterator<String> iterator = ingredientsSet.iterator();
        while(iterator.hasNext()) {
            String ingredientText = iterator.next();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientText);
            ingredientList.add(ingredient);
        }
        return  ingredientList;
    }


    private List<String> getSubjectsAndUnmodifiedWords() {
        List<String> list = Lists.newArrayList();

        list.add("со");
        list.add("до");
        list.add("по");
        list.add("за");
        list.add("на");
        list.add("и");
        list.add("или");
        list.add("може");
        list.add("малку");
        list.add(" ");
        list.add("");
        list.add("мл");
        list.add("гр");
        list.add("кг");
        return list;
    }

    private void addRecipesToInvertedIndex(long recipeID, Set<Ingredient> ingredients) {

        Iterator<Ingredient> ingredientIterator = ingredients.iterator();
        while(ingredientIterator.hasNext()) {
            Ingredient ingredient = ingredientIterator.next();
            String ingredientText = ingredient.getName();
            Set<Long> recipes = null;
            if (this.ingredientsContainedInRecipes.containsKey(ingredientText)) {
                 recipes = ingredientsContainedInRecipes.get(ingredientText);
            } else {
                recipes = Sets.newTreeSet();
            }
            recipes.add(recipeID);
            this.ingredientsContainedInRecipes.put(ingredientText, recipes);
        }

    }

    public TreeMap<String, Set<Long>> getIngredientsContainedInRecipes() {
        return ingredientsContainedInRecipes;
    }

    public void setIngredientsContainedInRecipes(TreeMap<String, Set<Long>> ingredientsContainedInRecipes) {
        this.ingredientsContainedInRecipes = ingredientsContainedInRecipes;
    }

    public void writeIngredientsInvertedIndexToFile(PrintWriter invertedIndexIngredientsWriter)
            throws JsonProcessingException {

        Iterator<Map.Entry<String, Set<Long>>> iterator = this.ingredientsContainedInRecipes.entrySet().iterator();
        ObjectMapper objectMapper = new ObjectMapper();

        while (iterator.hasNext()) {
            Map.Entry<String, Set<Long>> entry = iterator.next();
            String entryValueAsJsonString = objectMapper.writeValueAsString(entry);
            invertedIndexIngredientsWriter.println(entryValueAsJsonString);
        }

    }

    public void writeRecipeInfoToFile(Recipe recipe) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String recipeAsString = objectMapper.writeValueAsString(recipe);
        printWriterForRecipesData.println(recipeAsString);
        System.out.println(recipeAsString);
    }

}
