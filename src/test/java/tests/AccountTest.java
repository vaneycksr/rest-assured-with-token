package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AccountTest extends BaseTest {

    private static final String ACCOUNT_ENDPOINT = "/account";

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

}
