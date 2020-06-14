package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.game.WordTranslateGame;
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
        if (game.getLastTranslateDTO().getTranslate().equals(answer)) {} else {}
        new NextCardGameAction(game, principal).execute();
    }
}
