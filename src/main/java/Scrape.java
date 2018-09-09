import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * Created by Michael Jeszenka.
 * <a href="mailto:michael@jeszenka.com">michael@jeszenka.com</a>
 * 9/2/18
 */
public class Scrape {

    public static void main(String[] args) throws IOException {
        String url = "https://quizlet.com/149375456/cs6400-chapter-4-flash-cards/";
        String class1 = "SetPageTerm-wordText";
        String class2 = "SetPageTerm-definitionText";
        if (args.length > 0) {
            url = args[0];
            class1 = args[1];
            class2 = args[2];
        }
        Document document = Jsoup.connect(url).get();

        Elements links = document.getElementsByTag("div");
        Elements elements1 = document.getElementsByClass(class1);
        Elements elements2 = document.getElementsByClass(class2);
        List<String> front = new ArrayList<String>();
        List<String> back = new ArrayList<String>();


        for (Element e : elements1) {
            front.add(e.text());
        }

        for (Element e : elements2) {
            back.add(e.text());
        }

        Map<String, String> cards = new HashMap<>();

        int len = front.size();
        for (int i = 0; i < len; i++) {
            cards.put(front.get(i), back.get(i));
        }

        //System.out.println(cards);

        Set<String> keys = cards.keySet();

        for (String key : keys) {
            String value = cards.get(key);
            RequestSpecification request = new RequestSpecBuilder().
                    setBaseUri("http://localhost:8765").
                    setBasePath("").
                    addFilter(new RequestLoggingFilter()).
                    addFilter(new ResponseLoggingFilter()).
                    build();

            Response response =
                    given().
                            spec(request).
                            contentType(ContentType.JSON).
                            body("{\"action\": \"addNote\", \"version\": 6, \"params\": {\"note\": {\"deckName\": \"test\", \"modelName\": \"Basic\", \"fields\": { \"Front\": \"" + key + "\", \"Back\": \"" + value + "\"}, \"tags\": [\"\"]}}}").
                            when().
                            get("").
                            then().extract().response();
        }


    }







}
