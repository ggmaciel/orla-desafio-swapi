package com.ggmaciel.orlaapi.external;

import java.util.List;
import lombok.Data;

@Data
public class SwapiResponse {
    private List<SwapiFilm> results;
}
