package com.markuvinicius.views.implementation;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.views.ResponseView;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class BotExceptionView implements ResponseView {

    private String makeMessageText(BotException exception){
        StringBuilder builder = new StringBuilder();

        builder
                .append("<b>")
                .append(exception.getMessage())
                .append("</b>");

        return new String(builder);
    }

    @Override
    public SendMessage render(ModelMap model) {
        BotException exception = (BotException)model.getAttribute("exception");
        Long chatId = (Long)model.getAttribute("chat_id");

        SendMessage response = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(this.makeMessageText(exception))
                .enableNotification()
                .enableHtml(true);

        return response;
    }
}
