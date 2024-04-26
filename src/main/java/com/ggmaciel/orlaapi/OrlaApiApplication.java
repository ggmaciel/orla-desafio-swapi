package com.ggmaciel.orlaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslStoreBundle;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@SpringBootApplication
public class OrlaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrlaApiApplication.class, args);
    }

}
