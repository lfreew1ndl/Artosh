package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import com.exceed.app.web.websocket.game.dto.WordTranslateGameDTO;
import java.security.Principal;
import java.util.ArrayList;

public class NextCardGameAction extends GameAction {
    public static final String NEXT_CARD = "next_card";

    public NextCardGameAction(WordTranslateGame game, Principal principal) {
        super(game, principal);
    }

    @Override
    public void execute() {
        WordTranslateGameAction gameAction = new WordTranslateGameAction();
        gameAction.setAction(NEXT_CARD);
        game.setLastTranslateDTO(game.getTranslatedWordQueue().poll());

        WordTranslateGameDTO gameDTO = new WordTranslateGameDTO(
            0,
            game.getLastTranslateDTO().getWordWord(),
            game.getLastTranslateDTO().getTranslateOptions()
        );
        gameAction.setData(gameDTO);
        game.getMessagingTemplate().convertAndSend("/topic/game/" + game.getRoomId(), gameAction);
    }
}
