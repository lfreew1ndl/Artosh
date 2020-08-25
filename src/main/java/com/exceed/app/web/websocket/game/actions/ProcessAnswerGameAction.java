package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.service.dto.TranslateDTO;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessAnswerGameAction extends GameAction {
    private final String answer;
    private final Integer orderNumber;

    public ProcessAnswerGameAction(Principal principal, String answer, Integer orderNumber) {
        super(principal);
        this.answer = answer;
        this.orderNumber = orderNumber;
    }

    @Override
    public void execute() {
        if (!game.getOrderNumberOfWord().equals(orderNumber))
            return;

        TranslateDTO lastTranslateDTO = game.getLastTranslateDTO();
        WordTranslatePlayerData playerData = game.getPlayerDataMap().get(principal.getName());
        if (lastTranslateDTO.getTranslate().equals(answer)) {
            playerData.getCorrectTranslateWordDTOs().add(lastTranslateDTO);
        } else {
            playerData.getIncorrectTranslateWordDTOs().add(lastTranslateDTO);
        }

        if (game.getTranslatedWordQueue().isEmpty()) {
            this.game.execute(new FinishGameAction(principal));
        } else {
            this.game.execute(new NextCardGameAction(principal));
        }
    }
}
