package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.service.dto.TranslateDTO;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;
import java.security.Principal;

public class ProcessAnswerGameAction extends GameAction {
    public static final String GAME_CONFIRMED = "game_confirmed";
    private final String answer;

    public ProcessAnswerGameAction(WordTranslateGame game, Principal principal, String answer) {
        super(game, principal);
        this.answer = answer;
    }

    @Override
    public void execute() {
        TranslateDTO lastTranslateDTO = game.getLastTranslateDTO();
        WordTranslatePlayerData playerData = game.getPlayerDataMap().get(principal.getName());
        if (lastTranslateDTO.getTranslate().equals(answer)) {
            playerData.getCorrectTranslateWordDTOs().add(lastTranslateDTO);
        } else {
            playerData.getIncorrectTranslateWordDTOs().add(lastTranslateDTO);
        }
        new NextCardGameAction(game, principal).execute();
    }
}
