package com.dhg.applab.webchatbot.model;

import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
public class KeystrokeParams {
    private Character pressedKey;
    private OffsetDateTime timestamp;
    private Duration timeSinceLastKey;
}
