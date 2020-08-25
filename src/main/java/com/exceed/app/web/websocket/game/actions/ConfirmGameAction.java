package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import com.exceed.app.web.websocket.game.dto.StatusOfGame;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;
import java.security.Principal;
import java.util.Timer;
import java.util.TimerTask;

public class ConfirmGameAction extends GameAction {
    public static final String GAME_CONFIRMED = "GAME_CONFIRMED";

    public ConfirmGameAction(Principal principal) {
        super(principal);
    }

    @Override
    public void execute() {
        WordTranslatePlayerData playerData = game.getPlayerDataMap().get(principal.getName());

        playerData.setReady(true);
        if (!game.isReady()) {
            return;
        }

        game.setStatusOfGame(StatusOfGame.STARTED);
        WordTranslateGameAction gameAction = new WordTranslateGameAction();
        gameAction.setAction(GAME_CONFIRMED);
        gameAction.setData("The game is starting in 3 second"); //todo remove text
        game.getMessagingTemplate().convertAndSend("/topic/game/" + game.getRoomId(), gameAction);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                game.execute(new NextCardGameAction(principal));
            }
        };
        new Timer().schedule(timerTask, 3000);
    }
}
