package votes;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class VotesTest {

    private Integer voteId;

    @Before
    public void setup() {
        baseURI = "https://api.thecatapi.com";
        basePath = "/v1/votes";
    }

    @Test
    public void testDadoUmUsuarioQuandoVotaEmGostarDaImagemEntaoObtemStatusCode201() {

        given()
                .header("x-api-key", "live_Ln3MwKXoB0kSSrAd7SqEBJEoDQKyEmwfYIsyoPVuTkXjkteglhNp1JfVo2W6PczH")
                .body("{\n" +
                        "  \"image_id\": \"asf2\",\n" +
                        "  \"sub_id\": \"my-user-1234\",\n" +
                        "  \"value\": 0\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .log().all()
                .body("message", equalTo("SUCCESS"))
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id");

    }

    @Test
    public void testDadoUsuarioQuandoPesquisaVotoExistenteEntaoObtemStatusCode200() {

        voteId = armazenarVoteId();
        given()
                .header("x-api-key", "live_Ln3MwKXoB0kSSrAd7SqEBJEoDQKyEmwfYIsyoPVuTkXjkteglhNp1JfVo2W6PczH")
                .pathParam("voteId", voteId)
                .when()
                .get("/{voteId}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDadoUsuarioQuandoPesquisaVotoInexistenteEntaoObtemStatusCode404() {

        given()
                .header("x-api-key", "live_Ln3MwKXoB0kSSrAd7SqEBJEoDQKyEmwfYIsyoPVuTkXjkteglhNp1JfVo2W6PczH")
                .pathParam("voteId", 599035)
                .when()
                .get("/{voteId}")
                .then()
                .log().all()
                .body(equalTo("NOT_FOUND"))
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testDadoUsuarioQuandoExcluiVotoExistenteEntaoObtemStatusCode200() {

        voteId = armazenarVoteId();
        given()
                .header("x-api-key", "live_Ln3MwKXoB0kSSrAd7SqEBJEoDQKyEmwfYIsyoPVuTkXjkteglhNp1JfVo2W6PczH")
                .pathParam("voteId", voteId)
                .when()
                .delete("/{voteId}")
                .then()
                .log().all()
                .body("message", equalTo("SUCCESS"))
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDadoUsuarioQuandoExcluiVotoInexistenteEntaoObtemStatusCode404() {

        given()
                .header("x-api-key", "live_Ln3MwKXoB0kSSrAd7SqEBJEoDQKyEmwfYIsyoPVuTkXjkteglhNp1JfVo2W6PczH")
                .pathParam("voteId", 599035)
                .when()
                .delete("/{voteId}")
                .then()
                .log().all()
                .body(equalTo("NO_VOTE_FOUND_MATCHING_ID"))
                .assertThat()
                .statusCode(404);
    }


    private Integer armazenarVoteId() {

        Integer voteId = given()
                .header("x-api-key", "live_Ln3MwKXoB0kSSrAd7SqEBJEoDQKyEmwfYIsyoPVuTkXjkteglhNp1JfVo2W6PczH")
                .body("{\n" +
                        "  \"image_id\": \"asf2\",\n" +
                        "  \"sub_id\": \"my-user-1234\",\n" +
                        "  \"value\": 0\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .log().all()
                .body("message", equalTo("SUCCESS"))
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id");

        return voteId;
    }
}

