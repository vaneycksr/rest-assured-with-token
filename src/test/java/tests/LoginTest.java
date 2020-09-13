package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

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
                body(containsString("We're sorry, but this username or password was not found in our system."));

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
                body(containsString("We're sorry, but this username or password was not found in our system."));
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

                /**
                 * Comentei essa validação pois também comentei a validação que estava fazendo na classe BaseTest,
                 * onde verificava se as respostas das requisições retornavam em formato JSON. Como nesse API algumas respostas
                 * estão sendo retornadas no formato 'text/plain', estou fazendo as verificações apenas buscando alguns textos
                 * na string que é retornada.
                 * */
                //body("error", is("We're sorry, but this username or password was not found in our system."));
                body(containsString("We're sorry, but this username or password was not found in our system."));
    }

    /********** Testes utilizando token **********/

    @Test
    public void testVerificarSeUsuarioEstaLogadoUtilizandoTokenNoHeaderComInformacoesValidas(){

        User user = new User();
        user.setUsername("jsmith");
        user.setPassword("demo1234");

        given().
                headers("Authorization","Bearer " + extrairTokenDoUsuario(user.getUsername(), user.getPassword())).
        when().
                get(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("loggedin", is("true"));

    }

    @Test
    public void testVerificarSeUsuarioEstaLogadoSemPassarHeader(){

        given().
        when().
                get(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("Please log in first"));
    }

    @Test
    public void testVerificarSeUsuarioEstaLogadoSemPassarTokenApenasOBearer(){

        given().
                headers("Authorization","Bearer").
        when().
                get(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("Please log in first"));
    }

    @Test
    public void testVerificarUsuarioComParteDoTokenValido(){

        User user = new User();
        user.setUsername("jsmith");
        user.setPassword("demo1234");

        given().
                headers("Authorization","Bearer " + extrairTokenDoUsuario(user.getUsername(), user.getPassword()).substring(0,8)).
        when().
                get(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("Please log in first"));

    }

    @Test
    public void testVerificarUsuarioComTokenInvalido(){

        given().
                headers("Authorization","Bearer " + "ASASA#$\"@$@#DSADA32321/Sh4=").
        when().
                get(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("Please log in first"));
    }

    public String extrairTokenDoUsuario(String extractUser, String extractPassword){

        User user = new User();
        user.setUsername(extractUser);
        user.setPassword(extractPassword);

        return given().
                body(user).
        when().
                post(LOGIN_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                // extrai o token que está nesse campo
                path("Authorization");
    }

}
