package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import java.security.Principal;

public class StartGameAction extends GameAction {
    public static final String START_GAME = "start_game";

    public StartGameAction(WordTranslateGame game, Principal principal) {
        super(game, principal);
    }

    @Override
    public void execute() {
        WordTranslateGameAction gameAction = new WordTranslateGameAction();
        gameAction.setAction(START_GAME);
        game.getMessagingTemplate().convertAndSend("/topic/game/" + game.getRoomId(), gameAction);

        new NextCardGameAction(game, principal).execute();
    }
}
