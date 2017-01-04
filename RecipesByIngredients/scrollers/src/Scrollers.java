import model.Constants;
import serviceImpl.MoiRecepti.MoiReceptiAllIngredientsScroller;
import serviceImpl.MoiRecepti.MoiReceptiRecipeDataScroller;
import serviceImpl.MoiRecepti.MoiReceptiRecipesDatabaseWriter;

import java.io.*;
import java.util.List;
import java.util.TreeMap;

/**
 * Here all of the scrollers will be called.
 */
public class Scrollers {
    public static void main(String[] args) throws IOException {

       // takeDataFromMoiRecepti();
       saveDataFromMoiReceptiToDatabase();
    }

    public static void takeDataFromMoiRecepti() throws IOException {

        /* Scrolls all recipes urls from moi recepti.
        String allRecipesFileName = Constants.MOI_RECEPTI_ALL_RECIPES_FILE_NAME;
        FileWriter fileWriterAllRecipes =  new FileWriter(allRecipesFileName, true);
        BufferedWriter bufferedWriterAllRecipes =  new BufferedWriter(fileWriterAllRecipes);
        PrintWriter printWriterForAllRecipes = new PrintWriter(bufferedWriterAllRecipes);

        MoiReceptiAllRecipesScroller allRecipesScroller =
                new MoiReceptiAllRecipesScroller(printWriterForAllRecipes);

        allRecipesScroller.getAllRecipes();
        printWriterForAllRecipes.close();
        */


        String allIngredientsFileName = Constants.MOI_RECEPTI_ALL_INGREDIENTS_FILE_NAME;
        FileWriter fileWriterAllIngredients = new FileWriter(allIngredientsFileName, true);
        BufferedWriter bufferedWriterAllIngredients = new BufferedWriter(fileWriterAllIngredients);
        PrintWriter printWriterForAllIngredients = new PrintWriter(bufferedWriterAllIngredients);

        MoiReceptiAllIngredientsScroller allIngredientsScroller =
                new MoiReceptiAllIngredientsScroller(printWriterForAllIngredients);

       // TreeSet<String> ingredients = allIngredientsScroller.separateIngredients();

        TreeMap<String, List<String>> ingredients =  allIngredientsScroller.getCleanIngredientsSet();
        printWriterForAllIngredients.close();

        String recipesAndIngredientsFileName = Constants.MOI_RECEPTI_RECIPES_INFO_FILE_NAME;
        FileWriter fileWriterRecipesAndIngredients =  new FileWriter(recipesAndIngredientsFileName, true);
        BufferedWriter bufferedWriterRecipesAndIngredients =  new BufferedWriter(fileWriterRecipesAndIngredients);
        PrintWriter printWriterForRecipesAndIngredients = new PrintWriter(bufferedWriterRecipesAndIngredients);

        String ingredientsFileName = Constants.MOI_RECEPTI_INGREDIENTS_DESCRIPTION_FILE_NAME;
        FileWriter fileWriterIngredients =  new FileWriter(ingredientsFileName, true);
        BufferedWriter bufferedWriterIngredients =  new BufferedWriter(fileWriterIngredients);
        PrintWriter printWriterForIngredients = new PrintWriter(bufferedWriterIngredients);

        MoiReceptiRecipeDataScroller scroller =
                new MoiReceptiRecipeDataScroller(
                        printWriterForRecipesAndIngredients,
                        ingredients);

        scroller.scrollDocumentAndWriteToFile();

        String ingredientsWithInvertedIndex = Constants.MOI_RECEPTI_INGREDIENTS_INVERTED_INDEX_FILE_NAME;
        FileWriter fileWriterIngredientsWithInvertedIndex = new FileWriter(ingredientsWithInvertedIndex, true);
        BufferedWriter bufferedWriterIngredientsWithInvertedIndex  =
                new BufferedWriter(fileWriterIngredientsWithInvertedIndex);
        PrintWriter printWriterIngredientsWithInvertedIndex =
                new PrintWriter(bufferedWriterIngredientsWithInvertedIndex);

       // scroller.writeIngredientsInvertedIndexToFile(printWriterIngredientsWithInvertedIndex);
        printWriterIngredientsWithInvertedIndex.close();

        //System.out.println(scroller.getIngredientsContainedInRecipes());

        printWriterForIngredients.close();
        printWriterForRecipesAndIngredients.close();
    }

    public static void saveDataFromMoiReceptiToDatabase() {
        //FileReader
        MoiReceptiRecipesDatabaseWriter moiReceptiRecipesDatabaseWriter = new MoiReceptiRecipesDatabaseWriter();
       // moiReceptiRecipesDatabaseWriter.readIngredientsWithInvertedIndexAndWriteToDatabase();
        moiReceptiRecipesDatabaseWriter.readRecipesAndWriteToDatabase();
    }
}
