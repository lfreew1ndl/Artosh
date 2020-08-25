package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

public class FinishGameAction extends GameAction {
    public static final String END_GAME = "END_GAME";

    public FinishGameAction(Principal principal) {
        super(principal);
    }

    @Override
    public void execute() {
        WordTranslateGameAction gameAction = new WordTranslateGameAction();
        gameAction.setAction(END_GAME);
        int maxScore = game
            .getPlayerDataMap()
            .values()
            .stream()
            .mapToInt(
                wordTranslatePlayerData ->
                    wordTranslatePlayerData.getCorrectTranslateWordDTOs().size() -
                        wordTranslatePlayerData.getIncorrectTranslateWordDTOs().size()
            )
            .max()
            .orElse(0);

        List<WordTranslatePlayerData> playerData = game
            .getPlayerDataMap()
            .values()
            .stream()
            .filter(e -> (e.getCorrectTranslateWordDTOs().size() - e.getIncorrectTranslateWordDTOs().size()) == maxScore)
            .collect(Collectors.toList());

        if (playerData.size() == 1) {
            gameAction.setData("Winner " + playerData.get(0).getPlayerName());
        } else {
            gameAction.setData("Draw");
        }

        game.getMessagingTemplate().convertAndSend("/topic/game/" + game.getRoomId(), gameAction);
    }
}
