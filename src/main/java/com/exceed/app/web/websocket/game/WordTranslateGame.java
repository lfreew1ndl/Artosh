package com.exceed.app.web.websocket.game;

import com.exceed.app.domain.Word;
import com.exceed.app.repository.TranslateRepository;
import com.exceed.app.repository.WordRepository;
import com.exceed.app.service.dto.TranslateDTO;
import com.exceed.app.service.mapper.TranslateMapper;
import com.exceed.app.web.websocket.dto.PlayerDTO;
import com.exceed.app.web.websocket.dto.WordTranslateGameAction;
import com.exceed.app.web.websocket.game.actions.ConfirmGameAction;
import com.exceed.app.web.websocket.game.actions.GameAction;
import com.exceed.app.web.websocket.game.actions.ProcessAnswerGameAction;
import com.exceed.app.web.websocket.game.dto.StatusOfGame;
import com.exceed.app.web.websocket.game.dto.WordTranslatePlayerData;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Setter
@Getter
public class WordTranslateGame implements Runnable {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private TranslateRepository translateRepository;

    @Autowired
    private TranslateMapper translateMapper;

    private int roomId;
    private final List<PlayerDTO> players = new ArrayList<>();
    private final Map<String, WordTranslatePlayerData> playerDataMap = new LinkedHashMap<>();
    private LinkedList<TranslateDTO> translatedWordQueue;
    private TranslateDTO lastTranslateDTO;
    private StatusOfGame statusOfGame = StatusOfGame.NEW;
    private Integer orderNumberOfWord = 0;

    public WordTranslateGame() {
    }

    public void addPlayer(PlayerDTO player) {
        this.players.add(player);
    }

    public boolean isReady() {
        return playerDataMap.entrySet().stream().allMatch(e -> e.getValue().isReady());
    }

    @Override
    public void run() {
    }


    public synchronized void processAction(WordTranslateGameAction action, Principal principal) {
        if ("confirm_game".equals(action.getAction())) {
            execute(new ConfirmGameAction(principal));
        } else if ("answer".equals(action.getAction())) {
            List<Object> data = (List<Object>) action.getData();
            execute(new ProcessAnswerGameAction(principal, (String) data.get(0), (Integer) data.get(1)));
        }
    }


    public void execute(GameAction gameAction) {
        gameAction.setGame(this);
        gameAction.execute();
    }

    public void initializeGame() { //todo move to action
        LinkedList<Long> wordList = wordRepository
            .findAll()
            .stream()
            .limit(20)
            .map(Word::getId)
            .collect(Collectors.toCollection(LinkedList::new));

        this.translatedWordQueue = translateRepository
            .findAllById(wordList)
            .stream()
            .map(translateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        this.translatedWordQueue
            .forEach(
            e -> {
                Set<String> options = new HashSet<>();
                options.add(e.getTranslate());

                Random rand = new Random();
                while (options.size() < 4) {
                    int randomIndex = rand.nextInt(translatedWordQueue.size());
                    String randomElement = translatedWordQueue.get(randomIndex).getTranslate();
                    options.add(randomElement);
                }
                e.setTranslateOptions(options);
            }
        );

        for (PlayerDTO player : this.getPlayers()) {
            WordTranslatePlayerData playerData = new WordTranslatePlayerData(player.getUserLogin());
            this.playerDataMap.put(player.getUserLogin(), playerData);
        }
    }

    public Integer getOrderNumberOfWord() {
        return orderNumberOfWord;
    }

    public void setOrderNumberOfWord(Integer orderNumberOfWord) {
        this.orderNumberOfWord = orderNumberOfWord;
    }
}
