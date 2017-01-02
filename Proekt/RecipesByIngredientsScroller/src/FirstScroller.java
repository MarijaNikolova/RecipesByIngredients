import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by Marija on 10/30/2016.
 */
public class FirstScroller {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("http://www.foodnetworktv.com/recipes").get();
        Elements newsHeadlines = doc.select("div .listing");

        System.out.println(newsHeadlines);
    }
}
