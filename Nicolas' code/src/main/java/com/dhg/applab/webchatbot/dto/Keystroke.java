package com.dhg.applab.webchatbot.dto;

import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;

@Data
public class Keystroke {
    private Character pressedKey;
    private OffsetDateTime timestamp;
    private Duration timeSinceLastKey;

    public Keystroke(Character pressedKey, OffsetDateTime timestamp, Duration timeSinceLastKey) {
        this.pressedKey = pressedKey;
        this.timestamp = timestamp;
        this.timeSinceLastKey = timeSinceLastKey;
    }

    public static com.dhg.applab.webchatbot.dao.Keystroke toDao(final Keystroke dto) {
        return new com.dhg.applab.webchatbot.dao.Keystroke(
                dto.getPressedKey(),
                dto.getTimestamp(),
                dto.getTimeSinceLastKey()
        );
    }

    public static class KeystrokeBuilder {
        private Character pressedKey;
        private OffsetDateTime timestamp;
        private Duration timeSinceLastKey;

        public static KeystrokeBuilder from(final Keystroke dto) {
            return new KeystrokeBuilder()
                    .pressedKey(dto.getPressedKey())
                    .timestamp(dto.getTimestamp())
                    .timeSinceLastKey(dto.getTimeSinceLastKey());
        }

        public static KeystrokeBuilder fromDao(final com.dhg.applab.webchatbot.dao.Keystroke dao) {
            return new KeystrokeBuilder()
                    .pressedKey(dao.getPressedKey())
                    .timestamp(dao.getTimestamp())
                    .timeSinceLastKey(dao.getTimeSinceLastKey());
        }

        public KeystrokeBuilder pressedKey(Character pressedKey) {
            this.pressedKey = pressedKey;
            return this;
        }

        public KeystrokeBuilder timestamp(OffsetDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public KeystrokeBuilder timeSinceLastKey(Duration timeSinceLastKey) {
            this.timeSinceLastKey = timeSinceLastKey;
            return this;
        }

        public Optional<Keystroke> build() {
            return Optional.of(
                    new Keystroke(pressedKey, timestamp, timeSinceLastKey)
            );
        }
    }
}

