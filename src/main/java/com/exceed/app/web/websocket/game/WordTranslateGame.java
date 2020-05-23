package com.exceed.app.web.websocket.game;

import com.exceed.app.domain.Word;
import com.exceed.app.repository.CustomAuditEventRepository;
import com.exceed.app.web.websocket.dto.PlayerDTO;
import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.List;

public class WordTranslateGame {

    private int roomId;
    private final List<PlayerDTO> players = new ArrayList<>();
    private List<Word> wordList;

    public WordTranslateGame() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void addPlayer(PlayerDTO player) {
        this.players.add(player);
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }
}
