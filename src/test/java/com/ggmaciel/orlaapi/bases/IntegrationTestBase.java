package com.ggmaciel.orlaapi.bases;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestBase {

    @LocalServerPort
    private int port = 0;

    protected String url(final String context) {
        return "http://localhost:" + port + context;
    }
}
