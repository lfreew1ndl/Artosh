package com.exceed.app.web.websocket.game.actions;

import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import com.exceed.app.web.websocket.game.dto.WordTranslateGameDTO;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

public class NextCardGameAction extends GameAction {
    public static final String NEXT_CARD = "next_card";
    public static final String END_GAME = "END_GAME";

    public NextCardGameAction(WordTranslateGame game, Principal principal) {
        super(game, principal);
    }

    @Override
    public void execute() {
        WordTranslateGameAction gameAction = new WordTranslateGameAction();
        if (game.getTranslatedWordQueue().isEmpty()) { //todo move to finish method or activity
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
            return;
        }

        game.setLastTranslateDTO(game.getTranslatedWordQueue().poll());

        WordTranslateGameDTO gameDTO = new WordTranslateGameDTO(
            0,
            game.getLastTranslateDTO().getWordWord(),
            game.getLastTranslateDTO().getTranslateOptions()
        );
        gameAction.setAction(NEXT_CARD);
        gameAction.setData(gameDTO);
        game.getMessagingTemplate().convertAndSend("/topic/game/" + game.getRoomId(), gameAction);
    }
}
