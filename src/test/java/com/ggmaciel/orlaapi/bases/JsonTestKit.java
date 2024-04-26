package com.ggmaciel.orlaapi.bases;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@Slf4j
public class JsonTestKit {

    @Autowired
    private ObjectMapper objectMapper;

    public String jsonValue(String path) {
        try {
            File jsonFile = ResourceUtils.getFile("classpath:".concat(path));
            byte[] encoded = Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath()));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("error load content: {}", e.getMessage());
        }
        return null;
    }
}
