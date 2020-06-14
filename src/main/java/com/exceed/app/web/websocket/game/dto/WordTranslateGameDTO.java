package com.exceed.app.web.websocket.game.dto;

import java.util.Set;

public class WordTranslateGameDTO {
    private Integer orderNumberOfWord;
    private String currentWord;
    private Set<String> translateOptions;

    public WordTranslateGameDTO() {}

    public WordTranslateGameDTO(Integer orderNumberOfWord, String currentWord, Set<String> translateOptions) {
        this.orderNumberOfWord = orderNumberOfWord;
        this.currentWord = currentWord;
        this.translateOptions = translateOptions;
    }

    public Integer getOrderNumberOfWord() {
        return orderNumberOfWord;
    }

    public void setOrderNumberOfWord(Integer orderNumberOfWord) {
        this.orderNumberOfWord = orderNumberOfWord;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public Set<String> getTranslateOptions() {
        return translateOptions;
    }

    public void setTranslateOptions(Set<String> translateOptions) {
        this.translateOptions = translateOptions;
    }
}
