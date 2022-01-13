package io.tacsio.hazelcast.model;

import java.io.Serializable;

public class Rating implements Serializable {
    private String playerId;
    private String title;
    private Integer value;

    public Rating(String playerId, String title, Integer value) {
        this.playerId = playerId;
        this.title = title;
        this.value = value;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
