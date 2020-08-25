package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.game.WordTranslateGame;
import java.security.Principal;

public abstract class GameAction {
    protected WordTranslateGame game;
    protected final Principal principal;

    public GameAction(Principal principal) {
        this.principal = principal;
    }

    public void setGame(WordTranslateGame game){
        this.game = game;
    }

    public abstract void execute();
}
