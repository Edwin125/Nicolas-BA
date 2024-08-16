package com.dhg.applab.webchatbot.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class DateService {
    private static Optional<LocalDateTime> getLocalDateTime(final OffsetDateTime offsetDateTime) {
        return Optional.ofNullable(offsetDateTime)
                .map(odt -> odt.atZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime());
    }

    public static Optional<Timestamp> getTimestamp(final OffsetDateTime time) {
        return getLocalDateTime(time)
                .map(Timestamp::valueOf);
    }
}
