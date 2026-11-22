package dev.rudra.books;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/books";
    }

    private int createBook(String title) {
        return given().contentType(ContentType.JSON)
                .body(Map.of("title", title, "author", "Test Author", "pages", 120))
                .post()
                .then().statusCode(201)
                .extract().path("id");
    }

    @Test
    void createBookReturns201WithLocationAndBody() {
        given().contentType(ContentType.JSON)
                .body(Map.of("title", "Clean Code", "author", "Robert Martin", "pages", 464))
                .when().post()
                .then().statusCode(201)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("title", equalTo("Clean Code"));
    }

    @Test
    void getExistingBookReturnsIt() {
        int id = createBook("Refactoring");
        given().when().get("/{id}", id)
                .then().statusCode(200)
                .body("title", equalTo("Refactoring"));
    }

    @Test
    void getUnknownBookReturns404() {
        given().when().get("/{id}", 99999).then().statusCode(404);
    }

    @Test
    void blankTitleReturns400() {
        given().contentType(ContentType.JSON)
                .body(Map.of("title", " ", "author", "Someone", "pages", 10))
                .when().post()
                .then().statusCode(400);
    }

    @Test
    void zeroPagesReturns400() {
        given().contentType(ContentType.JSON)
                .body(Map.of("title", "Tiny", "author", "Someone", "pages", 0))
                .when().post()
                .then().statusCode(400);
    }

    @Test
    void deleteBookThenGetReturns404() {
        int id = createBook("To delete");
        given().when().delete("/{id}", id).then().statusCode(204);
        given().when().get("/{id}", id).then().statusCode(404);
    }

    @Test
    void listContainsCreatedBooks() {
        createBook("Listed book");
        given().when().get()
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }
}
