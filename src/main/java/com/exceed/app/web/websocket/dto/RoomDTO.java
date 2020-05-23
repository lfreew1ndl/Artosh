package com.exceed.app.web.websocket.dto;

public class RoomDTO {

    private Integer roomId;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
            "roomId=" + roomId +
            '}';
    }
}
