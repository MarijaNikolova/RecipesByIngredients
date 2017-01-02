import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import jdk.nashorn.internal.objects.NativeRegExp;
import model.Constants;
import serviceImpl.MoiRecepti.MoiReceptiAllIngredientsScroller;
import serviceImpl.MoiRecepti.MoiReceptiAllRecipesScroller;
import serviceImpl.MoiRecepti.MoiReceptiRecipeDataScroller;

import java.io.*;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Here all of the scrollers will be called.
 */
public class Scrollers {
    public static void main(String[] args) throws IOException {

        takeDataFromMoiRecepti();
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

        String recipesAndIngredientsFileName = "./resources/moirecepti/moirecepti-recipesinfo.txt";
        FileWriter fileWriterRecipesAndIngredients =  new FileWriter(recipesAndIngredientsFileName, true);
        BufferedWriter bufferedWriterRecipesAndIngredients =  new BufferedWriter(fileWriterRecipesAndIngredients);
        PrintWriter printWriterForRecipesAndIngredients = new PrintWriter(bufferedWriterRecipesAndIngredients);

        String ingredientsFileName = "moirecepti-ingredientsdescription.txt";
        FileWriter fileWriterIngredients =  new FileWriter(ingredientsFileName, true);
        BufferedWriter bufferedWriterIngredients =  new BufferedWriter(fileWriterIngredients);
        PrintWriter printWriterForIngredients = new PrintWriter(bufferedWriterIngredients);

        MoiReceptiRecipeDataScroller scroller =
                new MoiReceptiRecipeDataScroller(
                        printWriterForRecipesAndIngredients,
                        ingredients);

        scroller.scrollDocumentAndWriteToFile();

        String ingredientsWithInvertedIndex = "./resources/moirecepti/moirecepti-invertedindex.txt";
        FileWriter fileWriterIngredientsWithInvertedIndex = new FileWriter(ingredientsWithInvertedIndex, true);
        BufferedWriter bufferedWriterIngredientsWithInvertedIndex  =
                new BufferedWriter(fileWriterIngredientsWithInvertedIndex);
        PrintWriter printWriterIngredientsWithInvertedIndex =
                new PrintWriter(bufferedWriterIngredientsWithInvertedIndex);

        scroller.writeIngredientsInvertedIndexToFile(printWriterIngredientsWithInvertedIndex);
        printWriterIngredientsWithInvertedIndex.close();

        System.out.println(scroller.getIngredientsContainedInRecipes());

        printWriterForIngredients.close();
        printWriterForRecipesAndIngredients.close();
    }
}
