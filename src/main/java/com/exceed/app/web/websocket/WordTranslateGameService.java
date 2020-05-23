package com.exceed.app.web.websocket;

import com.exceed.app.domain.Word;
import com.exceed.app.repository.WordRepository;
import com.exceed.app.web.websocket.dto.PlayerDTO;
import com.exceed.app.web.websocket.dto.RoomDTO;
import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WordTranslateGameService implements ApplicationListener<SessionDisconnectEvent> {
    private static final Logger log = LoggerFactory.getLogger(com.exceed.app.web.websocket.LobbyService.class);

    private final WordRepository wordRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final List<WordTranslateGame> gameList = new ArrayList<>();

    public WordTranslateGameService(WordRepository wordRepository, SimpMessagingTemplate messagingTemplate) {
        this.wordRepository = wordRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic/word-translate-game/{roomId}")
    public void processAction(@DestinationVariable String roomId, @Payload WordTranslateGameAction action, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        WordTranslateGame game = gameList
            .stream()
            .filter(e -> e.getRoomId() == Integer.parseInt(roomId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Provided room does not exist"));

        messagingTemplate.convertAndSend("/topic/word-translate-game/" + roomId, "it works");
        log.debug("Game action {} in room {}", action, roomId);
    }

    public void createGame(RoomDTO roomDTO, PlayerDTO firstPlayer, PlayerDTO secondPlayer) {
        WordTranslateGame wordTranslateGame = new WordTranslateGame();
        wordTranslateGame.setRoomId(roomDTO.getRoomId());
        wordTranslateGame.addPlayer(firstPlayer);
        wordTranslateGame.addPlayer(secondPlayer);

        List<Word> wordList = wordRepository.findAll();

//        wordTranslateGame.setWordList();
        gameList.add(wordTranslateGame);
    }

    /**
     * Remove user games when the session is expired or finished.
     *
     * @param event session disconnect event
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
    }
}

