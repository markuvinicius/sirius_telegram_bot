package com.markuvinicius.models.words;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WordSyllables {
    private int count;
    private List<String> list;
}
