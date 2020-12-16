package com.markuvinicius.composer;

import com.markuvinicius.models.words.WordPronunciation;

public class WordPronunciationComposer {

    public static WordPronunciation build(){
        WordPronunciation pronunciation = new WordPronunciation();

        pronunciation.setAll("pronunciation");
        return pronunciation;
    }
}
