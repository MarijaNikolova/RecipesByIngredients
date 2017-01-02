package serviceImpl.MoiRecepti;

import model.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.AllRecipesUrlScroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Scroller for MoiRecepti.mk.
 */
public class MoiReceptiAllRecipesScroller implements AllRecipesUrlScroller {

    private static final int TOTAL_NUMBER_OF_SCROLLED_PAGES = 200;
    private static final int CURRENT_NUMBER_OF_PAGES  =  847;

    private PrintWriter printWriter;

    public MoiReceptiAllRecipesScroller(PrintWriter printWriter) throws IOException {

       this.printWriter = printWriter;
    }

    @Override
    public List<String> scrollDocument(Document document) throws IOException {

        List<String> recipesList = new ArrayList<>();

        Elements recipesUrls = document.select(Constants.MOI_RECEPTI_GET_ALL_RECIPES_FROM_A_PAGE_QUERY);

        for ( int k = 0; k < recipesUrls.size(); ++k) {

            Element urlElement = recipesUrls.get(k);

            String url = urlElement.attr("href");

            recipesList.add(url);
        }
        return recipesList;
    }

    @Override
    public void writeToFile(List<String> recipesList) {
        for (String recipeDetails : recipesList) {
            printWriter.println(recipeDetails);
        }
    }

    @Override
    public void getAllRecipes() throws IOException {

        Document document;
        List<String> recipesList;

        for (int pagesCounter = 1; pagesCounter <= Constants.MOI_RECEPTI_TOTAL_NUMBER_OF_PAGES; ++ pagesCounter) {

            String url = String.format("%s%d", Constants.MOI_RECEPTI_TEMPLATE_URL, pagesCounter);

            document = Jsoup.connect(url).get();

            recipesList = scrollDocument(document);

            writeToFile(recipesList);

            String log = String.format("Fetched recipes from %s", url);
            System.out.println(log);
        }

    }
}
