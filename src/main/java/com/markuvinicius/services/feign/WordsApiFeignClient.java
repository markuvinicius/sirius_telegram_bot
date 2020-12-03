package com.markuvinicius.services.feign;

import com.markuvinicius.configuration.feign.WordsApiFeignClientConfiguration;
import com.markuvinicius.models.words.WordComposition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="words-api", url="https://wordsapiv1.p.rapidapi.com", configuration = WordsApiFeignClientConfiguration.class)
public interface WordsApiFeignClient {

    @RequestMapping(method = RequestMethod.GET, value="/words")
    WordComposition getWordDefinitions(@RequestParam String word);
}
