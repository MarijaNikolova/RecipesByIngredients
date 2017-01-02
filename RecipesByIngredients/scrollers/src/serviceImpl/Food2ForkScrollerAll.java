package serviceImpl;

import model.Scroller;
import org.jsoup.nodes.Document;
import service.AllRecipesUrlScroller;

import java.io.IOException;
import java.util.List;

/**
 * Represents the scroller from Food2Fork page.
 */
public class Food2ForkScrollerAll extends Scroller implements AllRecipesUrlScroller {

    private String NAME = "Food2Fork";
    private String DEVELOPER_KEY = "b1ef79a5a348e53e819d388b53ffd243";
    private String URL = "http://food2fork.com/api/search?key=b1ef79a5a348e53e819d388b53ffd243";

    public Food2ForkScrollerAll(String name, String developerKey) {
        super(name, developerKey);
    }


    @Override
    public List<String> scrollDocument(Document document) throws IOException {
        return null;
    }

    @Override
    public void writeToFile(List<String> recipesList) {

    }

    @Override
    public void getAllRecipes() throws IOException {

    }

}
