package com.ggmaciel.orlaapi.bases;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;

@Component
public class SwapiMockTestKit {

    public void stubFilms(JsonTestKit jsonTestKit, MockRestServiceServer mockServer) {
        var responseMock = jsonTestKit.jsonValue("mocks/films.json");
        mockServer
                .expect(requestTo("https://swapi.dev/api/films"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseMock, MediaType.APPLICATION_JSON));
    }
}
