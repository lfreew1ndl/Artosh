package com.exceed.app.web.websocket;

import com.exceed.app.security.SecurityUtils;
import com.exceed.app.web.websocket.dto.GameDTO;
import com.google.common.collect.Lists;
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

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.exceed.app.config.WebsocketConfiguration.IP_ADDRESS;

@Controller //todo maybe split to service controller and scheduler
public class GameService implements ApplicationListener<SessionDisconnectEvent> {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    private final SimpMessagingTemplate messagingTemplate;

    private final List<GameDTO> lobby = new ArrayList<>();

    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic/game")
    public void findGame(@Payload GameDTO gameDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        if (lobby.stream().anyMatch(e -> e.getUserLogin().equals(principal.getName()))) { //validate that user is already in the lobby
            return;
        }
        gameDTO.setUserLogin(principal.getName());
        gameDTO.setSessionId(stompHeaderAccessor.getSessionId());
        gameDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        gameDTO.setTime(Instant.now());

        lobby.add(gameDTO);
        log.debug("User was added to lobby {}", gameDTO);
    }

    /**
     * Remove user games when the session is expired or finished.
     *
     * @param event session disconnect event
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        if (event.getUser() == null) return;

        List<GameDTO> gamesOfUser = lobby
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
        List<List<GameDTO>> partitions = Lists.partition(lobby, 2);
        partitions.stream().filter(e -> e.size() == 2) //todo maybe change because we don't need to iterate through whole list.
            .forEach((players) -> {
                GameDTO firstPlayer = players.get(0);
                GameDTO secondPlayer = players.get(1);
                lobby.remove(firstPlayer);
                lobby.remove(secondPlayer);
                messagingTemplate.convertAndSendToUser(firstPlayer.getUserLogin(), "/topic/game", "{\"room\": 200}");
                messagingTemplate.convertAndSendToUser(secondPlayer.getUserLogin(), "/topic/game", "{\"room\": 200}");
                log.debug("Game was started  {}", "{\"room\": 200}"); //todo change jsonString to object with autogenerated room
            });
    }
}
