package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;

public class LoginTest extends BaseTest {

    private static final String LOGIN_ENDPOINT = "/login";

    @Test
    public void testRealizarLoginComSucesso(){

        User user = new User();
        user.setUsername("jsmith");
        user.setPassword("demo1234");

        given().
                body(user).
        when().
                post(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK);

    }

}
