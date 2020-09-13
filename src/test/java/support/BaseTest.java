package support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

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
}
