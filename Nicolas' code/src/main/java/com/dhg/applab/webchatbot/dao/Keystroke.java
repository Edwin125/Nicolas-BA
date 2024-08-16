package com.dhg.applab.webchatbot.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
public class Keystroke {
    private Character pressedKey;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime timestamp;
    private Duration timeSinceLastKey;

    protected Keystroke() {

    }

    public Keystroke(Character pressedKey, OffsetDateTime timestamp, Duration timeSinceLastKey) {
        this.pressedKey = pressedKey;
        this.timestamp = timestamp;
        this.timeSinceLastKey = timeSinceLastKey;
    }

    @JsonProperty("pressedKey")
    public Character getPressedKey() {
        return pressedKey;
    }

    @JsonProperty("pressedKey")
    public void setPressedKey(Character pressedKey) {
        this.pressedKey = pressedKey;
    }

    @JsonProperty("timestamp")
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("timeSinceLastKey")
    public Duration getTimeSinceLastKey() {
        return timeSinceLastKey;
    }

    @JsonProperty("timeSinceLastKey")
    public void setTimeSinceLastKey(Duration timeSinceLastKey) {
        this.timeSinceLastKey = timeSinceLastKey;
    }
}
