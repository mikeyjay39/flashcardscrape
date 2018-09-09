import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;

/**
 * Created by Michael Jeszenka.
 * <a href="mailto:michael@jeszenka.com">michael@jeszenka.com</a>
 * 9/1/18
 */


public class SetupTests {

    static RequestSpecification request;

    @BeforeClass
    public void setup(){
        request = new RequestSpecBuilder().
                setBaseUri("http://localhost:8765").
                setBasePath("").
                addFilter(new RequestLoggingFilter()).
                addFilter(new ResponseLoggingFilter()).
                build();
    }
}
