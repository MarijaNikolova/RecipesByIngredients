package serviceImpl.MoiRecepti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.mysql.cj.jdbc.PreparedStatement;
import model.Constants;
import model.Ingredient;
import model.Recipe;

import java.io.*;
import java.sql.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.*;

/**
 * Class that is used to read the recipe data from files and save it to the database.
 */
public class MoiReceptiRecipesDatabaseWriter {

    ObjectMapper objectMapper = new ObjectMapper();

    public void readIngredientsWithInvertedIndexAndWriteToDatabase() {
        TreeMap<String, List<Integer>> ingredientsWithInvertedIndex = readIngredientsWithInvertedIndexFromFile();
        writeIngredientsToDatabase(ingredientsWithInvertedIndex);
    }

    public void readRecipesAndWriteToDatabase() {

        TreeSet<Recipe> recipes = readRecipesFromFile();
        writeRecipesToDatabase(recipes);
    }

    private TreeMap<String, List<Integer>> readIngredientsWithInvertedIndexFromFile() {

        TreeMap<String, List<Integer>> ingredientsWithInvertedIndex = new TreeMap<String, List<Integer>>();
        int counter = 0;
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(Constants.MOI_RECEPTI_INGREDIENTS_INVERTED_INDEX_FILE_NAME))){

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {

                Map<String, List<Integer>> entry = objectMapper.readValue(line, Map.class);
                ingredientsWithInvertedIndex.putAll(entry);
                System.out.println(entry.toString());
                System.out.println(counter);
                ++counter;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ingredientsWithInvertedIndex.toString());
        return ingredientsWithInvertedIndex;
    }

    private void writeIngredientsToDatabase(TreeMap<String, List<Integer>> ingredientsWithInvertedIndex) {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/recipesByIngredients";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "marija");

            String query1 = " insert into ingredient (id, name)"
                    + " values (?, ?)";
            String query2 = " insert into ingredientinvertedindex (id_ingredient, id_recipe)"
                    + " values (?, ?)";

            Iterator<Map.Entry<String, List<Integer>>> iterator = ingredientsWithInvertedIndex.entrySet().iterator();
            int counter = 0;
            while (iterator.hasNext()) {

                Map.Entry<String, List<Integer>> entry = iterator.next();
                PreparedStatement preparedStmt1 = (PreparedStatement) conn.prepareStatement(query1);
                preparedStmt1.setInt(1, counter);
                preparedStmt1.setString(2, entry.getKey());
                preparedStmt1.execute();
                List<Integer> recipes = entry.getValue();
                System.out.println(entry.getKey());
                for (Integer id : recipes) {
                    PreparedStatement preparedStmt2 = (PreparedStatement) conn.prepareStatement(query2);
                    preparedStmt2.setInt(1, counter);
                    preparedStmt2.setInt(2, id);
                    preparedStmt2.execute();
                }
                counter++;
            }

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    private TreeSet<Recipe>  readRecipesFromFile() {

        TreeSet<Recipe> recipes = new TreeSet<Recipe>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Constants.MOI_RECEPTI_RECIPES_INFO_FILE_NAME)))) {

            String line = "";
            while ((line = bufferedReader.readLine()) != null ) {
                Recipe recipe = objectMapper.readValue(line, Recipe.class);
                System.out.println(recipe.getId());
                recipes.add(recipe);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private void writeRecipesToDatabase(TreeSet<Recipe> recipes) {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/recipesByIngredients";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "marija");

            String query1 =
                    " insert into recipe (id, title, url, category, timeOfCooking, recipeYield, stepsForCooking, creationDate, imageUrl)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String query2 = " insert into recipeingredient (id_recipe, ingredient_description)"
                    + " values (?, ?)";

            Iterator<Recipe> iterator = recipes.iterator();
            int counter = 0;
            while (iterator.hasNext()) {

                Recipe recipe = iterator.next();
                PreparedStatement preparedStmt1 = (PreparedStatement) conn.prepareStatement(query1);
                preparedStmt1.setInt(1,((int) recipe.getId()));
                preparedStmt1.setString(2, recipe.getTitle());
                preparedStmt1.setString(3, recipe.getUrl());
                preparedStmt1.setString(4, recipe.getCategory());
                preparedStmt1.setString(5, recipe.getTimeOfCooking());
                preparedStmt1.setString(6, recipe.getRecipeYield());
                preparedStmt1.setString(7, recipe.getStepsForCooking());

                if (recipe.getCreationDate() != null) {
                    java.sql.Date creationDate = new java.sql.Date(recipe.getCreationDate().getTime());
                    preparedStmt1.setDate(8, creationDate);
                } else {
                    preparedStmt1.setDate(8, null);
                }
                preparedStmt1.setString(9, recipe.getPictureUrl());

                preparedStmt1.execute();

                Set<Ingredient> ingredients = recipe.getIngredientList();
                System.out.println(recipe.getId());
                for (Ingredient ingredient : ingredients) {
                    PreparedStatement preparedStmt2 = (PreparedStatement) conn.prepareStatement(query2);
                    preparedStmt2.setInt(1, (int)recipe.getId());
                    preparedStmt2.setString(2, ingredient.getDescription());
                    preparedStmt2.execute();
                }
                counter++;
            }

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}

