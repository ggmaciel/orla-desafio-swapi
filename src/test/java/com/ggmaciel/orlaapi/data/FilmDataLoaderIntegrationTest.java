package com.ggmaciel.orlaapi.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.ggmaciel.orlaapi.bases.IntegrationTestBase;
import com.ggmaciel.orlaapi.bases.JsonTestKit;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class FilmDataLoaderIntegrationTest extends IntegrationTestBase {

    @Autowired
    private FilmDataLoader filmDataLoader;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JsonTestKit jsonTestKit;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName(
            """
			given list of movies of star wars api
			when startup application
			should load all films in memory
			""")
    public void givenStarWarsApiShouldLoadAllFilms() throws IOException {

        var responseMock = jsonTestKit.jsonValue("mocks/films.json");

        mockServer
                .expect(requestTo("https://swapi.dev/api/films"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseMock, MediaType.APPLICATION_JSON));

        filmDataLoader.loadFilms();

        assertEquals(6, filmDataLoader.getFilms().size());
        // TODO: add more asserts
    }
}
