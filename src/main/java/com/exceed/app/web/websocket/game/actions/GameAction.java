package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.game.WordTranslateGame;
import java.security.Principal;

public abstract class GameAction {
    protected final WordTranslateGame game;
    protected final Principal principal;

    public GameAction(WordTranslateGame game, Principal principal) {
        this.game = game;
        this.principal = principal;
    }

    public abstract void execute();
}
