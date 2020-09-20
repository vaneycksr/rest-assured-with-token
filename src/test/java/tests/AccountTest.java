package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class AccountTest extends BaseTest {

    private static final String ACCOUNT_ENDPOINT = "/account";
    private static final String SINGLE_ACCOUNT_ENDPOINT = "/account/{accountId}";

    @Test
    public void testRealizarAccountComSucesso(){

        given().
                headers("Authorization","Bearer " +
                        extrairTokenDoUsuario(usuarioPadrao().getUsername(), usuarioPadrao().getPassword())).
        when().
                get(ACCOUNT_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("Accounts", is(notNullValue()));
    }

    @Test
    public void testRealizarAccountSemToken(){

        given().

        when().
                get(ACCOUNT_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("Please log in first"));
    }

    @Test
    public void testRealizarAccountEspecificoComSucesso(){

        given().
                headers("Authorization","Bearer " +
                        extrairTokenDoUsuario(usuarioPadrao().getUsername(), usuarioPadrao().getPassword())).
                pathParam("accountId", 1).
        when().
                get(SINGLE_ACCOUNT_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK);
    }

}
