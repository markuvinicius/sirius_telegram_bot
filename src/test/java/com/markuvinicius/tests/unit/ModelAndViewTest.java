package com.markuvinicius.tests.unit;

import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.views.ResponseView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.ModelMap;

public class ModelAndViewTest extends BasicUnitTest{

    @Mock
    private ResponseView view;

    private ModelAndView modelAndView;
    private Long chatId;

    @Before
    public void setup(){
        modelAndView = new ModelAndView();
        chatId = Long.MAX_VALUE;
    }

    @Test
    public void initMVCShouldStartInternalObjectMap(){
        Assertions.assertThat( modelAndView.getModelObjects() ).isNotNull();
        Assertions.assertThat( modelAndView.getModelObjects() ).isInstanceOf(ModelMap.class);
    }

    @Test
    public void setViewShouldSaveViewReference(){

        modelAndView.setView(view);
        Assertions.assertThat( modelAndView.getView() ).isNotNull();
        Assertions.assertThat( modelAndView.getView() ).isInstanceOf(ResponseView.class);
        Assertions.assertThat( modelAndView.getView() ).isEqualTo(view);
    }

    @Test
    public void addObjectShouldStoreKeyValuePair(){
        modelAndView.addObject("key",view);

        Assertions.assertThat( modelAndView.getObject("key") ).isNotNull();
        Assertions.assertThat( modelAndView.getObject("key").get() ).isInstanceOf(ResponseView.class);
        Assertions.assertThat( modelAndView.getObject("key").get() ).isEqualTo(view);
    }

    @Test
    public void MVCShouldStoreChatId(){
        modelAndView.setChatId(chatId);
        Assertions.assertThat( modelAndView.getChatId() ).isEqualTo(chatId);
    }



}
