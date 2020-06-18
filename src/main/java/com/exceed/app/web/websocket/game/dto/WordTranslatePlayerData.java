package com.exceed.app.web.websocket.game.dto;

import com.exceed.app.service.dto.TranslateDTO;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
public class WordTranslatePlayerData {
    private final String playerName;

    @Singular
    private List<TranslateDTO> correctTranslateWordDTOs = new LinkedList<>();

    @Singular
    private List<TranslateDTO> incorrectTranslateWordDTOs = new LinkedList<>();

    private boolean isReady = false;

    public WordTranslatePlayerData(String playerName) {
        this.playerName = playerName;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
