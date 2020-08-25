package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import com.exceed.app.web.websocket.game.dto.WordTranslateGameDTO;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

public class NextCardGameAction extends GameAction {
    public static final String NEXT_CARD = "NEXT_CARD";

    public NextCardGameAction(Principal principal) {
        super(principal);
    }

    @Override
    public void execute() {
        WordTranslateGameAction gameAction = new WordTranslateGameAction();
        game.setOrderNumberOfWord(game.getOrderNumberOfWord() + 1);
        game.setLastTranslateDTO(game.getTranslatedWordQueue().poll());

        WordTranslateGameDTO gameDTO = new WordTranslateGameDTO(
            game.getOrderNumberOfWord(),
            game.getLastTranslateDTO().getWordWord(),
            game.getLastTranslateDTO().getTranslateOptions()
        );
        gameAction.setAction(NEXT_CARD);
        gameAction.setData(gameDTO);
        game.getMessagingTemplate().convertAndSend("/topic/game/" + game.getRoomId(), gameAction);
    }
}
