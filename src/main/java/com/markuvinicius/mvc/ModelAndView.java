package com.markuvinicius.mvc;

import com.markuvinicius.views.ResponseView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.ModelMap;

import java.util.Optional;

public class ModelAndView {

    @Getter
    @Setter
    private Long chatId;
    private ModelMap modelObjects;
    private ResponseView view;

    public ModelAndView(){
        this.modelObjects = new ModelMap();
    }

    public void setView(ResponseView view) {
        this.view = view;
    }

    public void addObject(String objectKey, Object objectValue) {
        this.modelObjects.addAttribute(objectKey,objectValue);
    }

    public Optional<Object> getObject(String objectKey){
        return Optional.ofNullable(modelObjects.getAttribute(objectKey));
    }

    public ResponseView getView(){
        return this.view;
    }

    public ModelMap getModelObjects() {
        return modelObjects;
    }
}
