package com.markuvinicius.services;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlParser {

    UrlDetector detector;

    public List<Url> parseUrl(String text){
        UrlDetector parser = new UrlDetector(text, UrlDetectorOptions.Default);
        return parser.detect();
    }
}
