package com.ggmaciel.orlaapi.domain.film;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ggmaciel.orlaapi.bases.IntegrationTestBase;
import com.ggmaciel.orlaapi.bases.JsonTestKit;
import com.ggmaciel.orlaapi.bases.SwapiMockTestKit;
import com.ggmaciel.orlaapi.data.FilmDataLoader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class FilmControllerTest extends IntegrationTestBase {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JsonTestKit jsonTestKit;

    @Autowired
    private SwapiMockTestKit swapiMockTestKit;

    @Autowired
    private FilmDataLoader filmDataLoader;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        swapiMockTestKit.stubFilms(jsonTestKit, mockServer);

        filmDataLoader.loadFilms();
    }

    @DisplayName(
            """
			given a film id and a new description
			when update film
			should update just a description
			""")
    @Test
    public void shouldUpdateFilm() {
        var request = """
				{
				"opening_crawl": "new description"
				}
				""";
        var response = RestAssured.given()
                .contentType("application/json")
                .body(request)
                .put(url("/v1/films/4"))
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());
        assertEquals("new description", response.jsonPath().getString("opening_crawl"));
    }

    @DisplayName(
            """
			given a film id incorrect and a new description
			when update film
			should return status code not found
			""")
    @Test
    public void shouldStatusCodeNotFound() {
        var request = """
				{
				"opening_crawl": "new description"
				}
				""";
        var response = RestAssured.given()
                .contentType("application/json")
                .body(request)
                .put(url("/v1/films/100"))
                .then()
                .extract()
                .response();

        assertEquals(404, response.getStatusCode());
    }

    @DisplayName("""
			given a film id
			when show film
			should return data of film
			""")
    @Test
    public void shouldShowFilm() {
        var response = RestAssured.given()
                .contentType("application/json")
                .get(url("/v1/films/4"))
                .then()
                .extract()
                .response();
        assertEquals(200, response.getStatusCode());

        assertEquals("A New Hope Mocked", response.jsonPath().getString("title"));
        assertEquals(4, response.jsonPath().getInt("episode_id"));
        assertEquals("George Lucas", response.jsonPath().getString("director"));
    }

    @DisplayName("""
			given a film id incorrect
			when show film
			should return status code 404
			""")
    @Test
    public void shouldReturnFilmNotFound() {
        var response = RestAssured.given()
                .contentType("application/json")
                .get(url("/v1/films/10000"))
                .then()
                .extract()
                .response();
        assertEquals(404, response.getStatusCode());
    }

    @DisplayName("""
			when list all films
			should return all films
			""")
    @Test
    public void shouldReturnAllFilms() {
        var response = RestAssured.given()
                .contentType("application/json")
                .get(url("/v1/films"))
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals(6, response.jsonPath().getInt("size()"));
        assertEquals("The Phantom Menace", response.jsonPath().getString("[0].title"));
        assertEquals(1, response.jsonPath().getInt("[0].version"));
        assertEquals("Attack of the Clones", response.jsonPath().getString("[1].title"));
    }

    @DisplayName("""
			given a saga id
			when list all films
			should return all films filtered by saga
			""")
    @Test
    public void shouldReturnAllFilmsBySagaId() {
        var response = RestAssured.given()
                .contentType("application/json")
                .get(url("/v1/films?saga_id=4"))
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals(1, response.jsonPath().getInt("size()"));
        assertEquals("A New Hope Mocked", response.jsonPath().getString("[0].title"));
        assertEquals(1, response.jsonPath().getInt("[0].version"));
    }
}
