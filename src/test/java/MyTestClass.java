import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Created by Michael Jeszenka.
 * <a href="mailto:michael@jeszenka.com">michael@jeszenka.com</a>
 * 9/1/18
 */


public class MyTestClass extends SetupTests{

    @Test
    public void firstTest() {

        Response response =
                given().
                        spec(request).
                        contentType(ContentType.JSON).
                        body("{\"action\": \"deckNames\", \"version\": 6}").
                when().
                        get("").
                then().extract().response();
        Assert.assertEquals(response.body().asString(), "{\"result\": [\"Bible verses\", \"C\", \"Computer Architecture\", \"Design Patterns\", \"Computer Networks\", \"Discrete Math\", \"Python3\", \"magyar szavak \", \"Java\", \"Radiotelephony alphabet\", \"Default\", \"databases\", \"misc\", \"DSALG_ICT\"], \"error\": null}");
    }
}
