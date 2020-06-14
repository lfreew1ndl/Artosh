package com.exceed.app.web.websocket.game.dto;

import com.exceed.app.service.dto.TranslateDTO;
import java.util.LinkedList;
import java.util.List;

public class WordTranslatePlayerData {
    private final String playerName;
    private List<TranslateDTO> translateWordDTOs = new LinkedList<>();
    private boolean isReady = false;

    public WordTranslatePlayerData(String playerName) {
        this.playerName = playerName;
    }

    public List<TranslateDTO> getTranslateWordDTOs() {
        return translateWordDTOs;
    }

    public void setTranslateWordDTOs(List<TranslateDTO> translateWordDTOs) {
        this.translateWordDTOs = translateWordDTOs;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
