package com.markuvinicius.views;

import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ResponseView {

    SendMessage render(ModelMap model);
}
