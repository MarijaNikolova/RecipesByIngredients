package serviceImpl.MoiRecepti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.PreparedStatement;
import model.Constants;
import java.sql.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.*;

/**
 * Class that is used to read the recipe data from files and save it to the database.
 */
public class MoiReceptiRecipesDatabaseWriter {

    ObjectMapper objectMapper = new ObjectMapper();

    public void readIngredientsWithInvertedIndexAndWriteToFile() {
        TreeMap<String, List<Integer>> ingredientsWithInvertedIndex = readIngredientsWithInvertedIndexFromFile();
        writeIngredientsToDatabase(ingredientsWithInvertedIndex);
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
}

