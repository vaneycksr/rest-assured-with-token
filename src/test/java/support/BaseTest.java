package support;

import domain.User;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;

public class BaseTest {

    @BeforeClass
    public static void setUp(){

        // habilita os logs da requisição
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // uri padrão para todos os endpoints
        baseURI = "http://demo.testfire.net";

        // path padrão
        basePath = "/api";

        // define que todas as requisições que serão enviadas vão ser do tipo JSON
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        // define que todas respostas das requisições vão ser do tipo JSON
//        RestAssured.responseSpecification = new ResponseSpecBuilder()
//                .expectContentType(ContentType.JSON)
//                .build();
    }

    public String extrairTokenDoUsuario(String extractUser, String extractPassword){

        User user = new User();
        user.setUsername(extractUser);
        user.setPassword(extractPassword);

        return given().
                body(user).
            when().
                post("/login").
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                // extrai o token que está nesse campo
                path("Authorization");
    }

    public User usuarioPadrao(){

        User user = new User();
        user.setUsername("jsmith");
        user.setPassword("demo1234");

        return user;
    }
}
