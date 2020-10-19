package com.markuvinicius.views.implementation;

import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.views.ResponseView;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WordDefinitionDetailsView implements ResponseView {
    @Override
    public SendMessage render(ModelMap model) {
        WordDefinition wordDefinition = (WordDefinition)model.getAttribute("word_definition");
        Long chatId = (Long)model.getAttribute("chat_id");

        SendMessage response = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(this.makeMessageText(wordDefinition))
                .enableNotification()
                .enableHtml(true);

        return response;
    }

    private String makeMessageText(WordDefinition definition){
        StringBuilder builder = new StringBuilder();

        builder
            .append("[")
            .append(definition.getPartOfSpeech())
            .append("] - ")
            .append( definition.getDefinition());

        if (definition.hasExamples()){
            builder
                .append("\n")
                .append("Examples: [");

            definition.getExamples().forEach( example -> builder
                                                            .append("\n")
                                                            .append("<pre>")
                                                                .append("'")
                                                                .append("<i>")
                                                                .append(example)
                                                                .append("'")
                                                            .append("</i>")
                                                            .append("</pre>")
            );

            builder.append("\n ]");
        }

        if (definition.hasSynonyms() ){
            builder
                    .append("\n")
                    .append("Synonyms: [");

            definition.getSynonyms().forEach( synonym -> builder
                    .append("\n")
                    .append("<pre>")
                    .append("'")
                    .append("<i>")
                    .append(synonym)
                    .append("'")
                    .append("</i>")
                    .append("</pre>")
            );

            builder.append("\n ]");
        }

        if (definition.hasSimilarTo() ){
            builder
                    .append("\n")
                    .append("Similar To: [");

            definition.getSimilarTo().forEach( similarTo -> builder
                    .append("\n")
                    .append("<pre>")
                    .append("'")
                    .append("<i>")
                    .append(similarTo)
                    .append("'")
                    .append("</i>")
                    .append("</pre>")
            );

            builder.append("\n ]");
        }

        return new String(builder);
    }
}
