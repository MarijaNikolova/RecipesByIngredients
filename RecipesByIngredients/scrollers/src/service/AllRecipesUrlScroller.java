package service;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Represents all the methods the scrollers for the recipes should have.
 */
public interface AllRecipesUrlScroller {

    /**
     * Takes all the elements from one page.
     * @param document {@link Document}
     * @return {@link List}
     * @throws IOException
     */
    public List<String> scrollDocument(Document document) throws IOException;

    /**
     * Writes the list with recipes urls to specified file.
     * @param recipesList {@link List}.
     */
    public void writeToFile(List<String> recipesList);

    /**
     * Scrolls all the recipes from a page.
     */
    public void getAllRecipes() throws IOException ;
}
