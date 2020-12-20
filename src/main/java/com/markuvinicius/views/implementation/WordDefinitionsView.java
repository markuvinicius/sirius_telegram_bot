package com.markuvinicius.views.implementation;

import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.views.ResponseView;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WordDefinitionsView implements ResponseView {
    private final int MAX_BUTTONS_INLINE = 8;

    private String makeCallBackData(String word, int definitionIndex){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("word")
                .append("/")
                .append(word)
                .append("/")
                .append(definitionIndex);

        return new String(stringBuilder);
    }

    private String makeMessageText(WordComposition results){
        StringBuilder builder = new StringBuilder();

        builder
                .append("word: <b>").append(results.getWord().toUpperCase()).append("</b>")
                .append("\n");

        builder
                .append("<b>Pronunciation: ").append(results.getPronunciation().getAll()).append("</b>")
                .append("\n\n");

        int i=1;
        for (WordDefinition definition : results.getResults()){
            builder
                    .append("<b>#")
                        .append(i)
                    .append("</b> - ")
                        .append("[")
                        .append(definition.getPartOfSpeech())
                        .append("] - ")
                    .append( definition.getDefinition())
                    .append("\n");

            i += 1;
        }
        builder.append("\n\n");
        return new String(builder);
    }

    private InlineKeyboardMarkup makeOptionButtons(WordComposition results){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        String word = results.getWord();

        int menuIndex = 1;
        Iterator<WordDefinition> definitionsIterator = results.getResults().iterator();

        while (definitionsIterator.hasNext()){
            WordDefinition wordDefinition = definitionsIterator.next();

            //creates the button
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton()
                    .setText("#" + menuIndex)
                    .setCallbackData( this.makeCallBackData(word,menuIndex-1));

            rowInline.add(inlineKeyboardButton);

            //iterates the menu index
            menuIndex += 1;
        }


        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    @Override
    public SendMessage render(ModelMap model) {
        WordComposition wordComposition = (WordComposition)model.getAttribute("word_composition");
        Long chatId = (Long)model.getAttribute("chat_id");

        return new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(this.makeMessageText(wordComposition))
                .setReplyMarkup(this.makeOptionButtons(wordComposition))
                .enableNotification()
                .enableHtml(true);
    }
}
