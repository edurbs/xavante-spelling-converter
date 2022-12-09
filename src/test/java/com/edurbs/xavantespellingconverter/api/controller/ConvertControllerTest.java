package com.edurbs.xavantespellingconverter.api.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.edurbs.xavantespellingconverter.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConvertControllerTest {

    @LocalServerPort
    private int port;

    private String textExpected;

    @BeforeEach
    public void prepare(){

        this.textExpected = "(Jesus watsuꞌu. Barjesus.) Ma tô ꞌmadöꞌö dzaꞌra. Tserehe ma: wahöimanadzé, ĩ̱höimanadzé, Ĩ̱höimanadzé, ĩhöimanadzé, Ĩhöimanadzé watsété.";

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/convert";
    }


    @Test
    public void shouldReturnOKWithTextConverted_whenPostText() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(ResourceUtils.getContentFromResource("/json/textOK.json"))
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("textConverted", Matchers.equalTo(textExpected));            
    }

    @Test
    public void shouldReturn400WithBlankText_whenPostText() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(ResourceUtils.getContentFromResource("/json/textBlank.json"))
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());                
    }
}
