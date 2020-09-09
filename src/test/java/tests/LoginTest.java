package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

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
                statusCode(HttpStatus.SC_OK).
                body("success",is(user.getUsername()+" is now logged in")).
                body("Authorization",is(notNullValue()));
    }

    @Test
    public void testLoginComUsernameInvalidoEPasswordCorreto(){

        User user = new User();
        user.setUsername("qqq");
        user.setPassword("demo1234");

        given().
                body(user).
        when().
                post(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("We're sorry, but this username or password was not found in our system."));

    }

    @Test
    public void testLoginComUsernameCorretoEPasswordInvalido(){

        User user = new User();
        user.setUsername("jsmith");
        user.setPassword("!@###");

        given().
                body(user).
        when().
                post(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("We're sorry, but this username or password was not found in our system."));

    }

    @Test
    public void testLoginComUsernameInvalidoEPasswordInvalido(){

        User user = new User();
        user.setUsername("van");
        user.setPassword("!@###");

        given().
                body(user).
        when().
                post(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("We're sorry, but this username or password was not found in our system."));
    }
}
