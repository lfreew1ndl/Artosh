package com.exceed.app.web.websocket;

import com.exceed.app.web.websocket.dto.PlayerDTO;
import com.exceed.app.web.websocket.dto.RoomDTO;
import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class GameService implements ApplicationListener<SessionDisconnectEvent> {
    private static final Logger log = LoggerFactory.getLogger(com.exceed.app.web.websocket.LobbyService.class);

    private final List<WordTranslateGame> gameList = new ArrayList<>();
    private final Function<String, WordTranslateGame> gameFactory;

    public GameService(Function<String, WordTranslateGame> gameFactory) {
        this.gameFactory = gameFactory;
    }

    @MessageMapping("/topic/game/{roomId}")
    public void processAction(
        @DestinationVariable("roomId") String roomId,
        @Payload WordTranslateGameAction action,
        StompHeaderAccessor stompHeaderAccessor,
        Principal principal
    ) {
        WordTranslateGame game = gameList
            .stream()
            .filter(e -> e.getRoomId() == Integer.parseInt(roomId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Provided room does not exist"));

        game.processAction(action, principal);
        log.debug("Game action {} in room {}", action, roomId);
    }

    public void createGame(RoomDTO roomDTO, PlayerDTO firstPlayer, PlayerDTO secondPlayer) {
        WordTranslateGame wordTranslateGame = gameFactory.apply(WordTranslateGame.class.getName());
        wordTranslateGame.setRoomId(roomDTO.getRoomId());
        wordTranslateGame.addPlayer(firstPlayer);
        wordTranslateGame.addPlayer(secondPlayer);
        wordTranslateGame.initializeGame();
        gameList.add(wordTranslateGame);
    }

    /**
     * Remove user games when the session is expired or finished.
     *
     * @param event session disconnect event
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {}
}
