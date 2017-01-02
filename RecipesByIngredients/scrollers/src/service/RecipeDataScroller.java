package service;

import model.Ingredient;
import model.Recipe;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Represents all the methods the scrollers for the recipes and ingredients should have.
 */
public interface RecipeDataScroller {

    /**
     * Takes all data for a recipe from the document.
     * @param document
     * @return
     */
    public Recipe getRecipeInfo(Document document);

    /**
     * Writes the data for the recipe to file.
     * @param recipe
     */
    public void writeRecipeToFile(Recipe recipe);

    /**
     * Selects the creation date from the document.
     * @param recipe {@link Recipe}
     * @param document {@link Document}
     */
    public void getCreationDate(Recipe recipe, Document document);

    /**
     * Selects the recipe's title from the document.
     * @param recipe {@link Recipe}
     * @param document {@link Document}
     */
    public void getTitle(Recipe recipe, Document document);

    /**
     * Selects the preparation (time of cooking, persons, category) from the document.
     * @param recipe {@link Recipe}
     * @param document {@link Document}
     */
    public void getPreparationInfo(Recipe recipe, Document document);

    /**
     * Selects the ingredients for the recipe from the document.
     * @param recipe {@link Recipe}
     * @param document {@link Document}
     */
    public void getIngredients(Recipe recipe, Document document);

    /**
     * Select
     * @param recipe
     * @param document
     */
    public void getImage(Recipe recipe, Document document);

    public void getPreparationSteps(Recipe recipe, Document document);
}
