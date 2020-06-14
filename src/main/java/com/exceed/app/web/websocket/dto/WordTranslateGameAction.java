package com.exceed.app.web.websocket.dto;

public class WordTranslateGameAction {
    private String action;

    private Object data;

    public WordTranslateGameAction() {}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WordTranslateGameAction{" + "action='" + action + '\'' + ", data=" + data + '}';
    }
}
