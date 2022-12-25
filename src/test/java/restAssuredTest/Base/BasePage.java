package restAssuredTest.Base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static restAssuredTest.Constants.Constants.changeColor;
import static restAssuredTest.Constants.Constants.updateCard;

public class BasePage {
    public static HashMap map = new HashMap<>();
    public void setUp(){
        map.put("key", "7817f7f10978461629f7ae7f98c1cfd2");
        map.put("token", "f217618b93c3c0b5ffa11de01e70955fad14a6505d13e5742d62863e0c9820b0");
        RestAssured.baseURI = "https://api.trello.com/1";
    }

    public Response post(String name,String path){
        return given().header("Accept-Encoding", "gzip,deflate")
                .contentType("application/json")
                .queryParam("name", name)
                .body(map)
                .log().all()
                .when()
                .post(path);
    }
    public Response post(String name,String path,String paramName,String paramValue){
        return given().header("Accept-Encoding", "gzip,deflate")
                .contentType("application/json")
                .queryParam("name", name)
                .queryParam(paramName, paramValue)
                .body(map)
                .log().all()
                .when()
                .post(path);
    }
    public String extractBodyId(Response response){
        return (String) response.then()
                .extract().jsonPath().getMap("$").get("id");
    }
    public void deleteItem(String pathParam,String pathParamValue,String path){
        given().header("Accept-Encoding", "gzip,deflate")
                .pathParam(pathParam, pathParamValue)
                .body(map)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete(path)
                .then()
                .statusCode(200);
    }
    public void updateCards(String cardId){
        given().header("Accept-Encoding", "gzip,deflate")
                .pathParam("cardId", cardId)
                .queryParam("key", "7817f7f10978461629f7ae7f98c1cfd2")
                .queryParam("token", "f217618b93c3c0b5ffa11de01e70955fad14a6505d13e5742d62863e0c9820b0")
                .body(changeColor)
                .contentType(ContentType.JSON)
                .log().all().
                when()
                .put(updateCard).
                then()
                .statusCode(200);
    }
    public void assertName(Response response,String name){
        response.then()
                .statusCode(200)
                .body("name", equalTo(name));
    }
}