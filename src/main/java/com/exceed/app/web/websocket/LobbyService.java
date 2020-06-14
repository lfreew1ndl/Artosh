package com.exceed.app.web.websocket;

import static com.exceed.app.config.WebsocketConfiguration.IP_ADDRESS;

import com.exceed.app.web.websocket.dto.PlayerDTO;
import com.exceed.app.web.websocket.dto.RoomDTO;
import com.google.common.collect.Lists;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class LobbyService implements ApplicationListener<SessionDisconnectEvent> {
    private static final Logger log = LoggerFactory.getLogger(LobbyService.class);

    private final SimpMessagingTemplate messagingTemplate;

    private final GameService gameService;

    private final List<PlayerDTO> lobby = new ArrayList<>();

    public LobbyService(SimpMessagingTemplate messagingTemplate, GameService gameService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
    }

    @MessageMapping("/topic/lobby")
    public void findGame(@Payload PlayerDTO playerDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        if (lobby.stream().anyMatch(e -> e.getUserLogin().equals(principal.getName()))) { //validate that user is already in the lobby
            return;
        }
        playerDTO.setUserLogin(principal.getName());
        playerDTO.setSessionId(stompHeaderAccessor.getSessionId());
        playerDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        playerDTO.setTime(Instant.now());

        lobby.add(playerDTO);
        log.debug("User was added to lobby {}", playerDTO);
    }

    /**
     * Remove user games when the session is expired or finished.
     *
     * @param event session disconnect event
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        List<PlayerDTO> gamesOfUser = lobby
            .stream()
            .filter(e -> e.getSessionId().equals(event.getSessionId()))
            .collect(Collectors.toList());
        lobby.removeAll(gamesOfUser);
    }

    /**
     * Create game when in lobby enough players
     * <p>
     * This is scheduled to to create game every 10 second.
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void startGameScheduler() {
        List<List<PlayerDTO>> partitions = Lists.partition(lobby, 2);
        int randomRoomId = new Random().nextInt(); //todo it can be repeated so replace with some sequence
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId(randomRoomId);

        partitions
            .stream()
            .filter(e -> e.size() == 2) //todo maybe change because we don't need to iterate through whole list.
            .forEach(
                players -> {
                    PlayerDTO firstPlayer = players.get(0);
                    PlayerDTO secondPlayer = players.get(1);
                    lobby.remove(firstPlayer);
                    lobby.remove(secondPlayer);
                    gameService.createGame(roomDTO, firstPlayer, secondPlayer);
                    messagingTemplate.convertAndSendToUser(firstPlayer.getUserLogin(), "/topic/lobby", roomDTO);
                    messagingTemplate.convertAndSendToUser(secondPlayer.getUserLogin(), "/topic/lobby", roomDTO);
                    log.debug("Game was started  {}", roomDTO);
                }
            );
    }
}
