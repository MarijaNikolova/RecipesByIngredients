package service;

import model.Ingredient;
import model.Recipe;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Interface that represents the behaviour of the ingredients scrollers.
 */
public interface AllIngredientsScroller {

    /**
     * Gets the ingredients from a document.
     * @param document - the recipe page.
     */
    public List<Ingredient> getIngredientsFromRecipe(Document document);

    /**
     * Writes all ingredients to file.
     */
    public void writeIngredientsToFile(List<Ingredient> ingredients);

}