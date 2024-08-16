package com.dhg.applab.webchatbot.mapper;

import com.dhg.applab.webchatbot.dto.Conversation;
import com.dhg.applab.webchatbot.dto.Keystroke;
import com.dhg.applab.webchatbot.model.ConversationModel;
import com.dhg.applab.webchatbot.model.KeystrokeParams;
import com.dhg.applab.webchatbot.utils.DateService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationMapper {
    public static Conversation createNewConversation(final Integer id) {
        return new Conversation.ConversationBuilder()
                .created(OffsetDateTime.now())
                .id(id)
                .build()
                .orElseThrow();
    }

    public static List<Keystroke> translateKeystroke(final List<KeystrokeParams> keystrokes) {
        return keystrokes.stream()
                .map(keystroke -> new Keystroke.KeystrokeBuilder()
                        .timestamp(keystroke.getTimestamp())
                        .pressedKey(keystroke.getPressedKey())
                        .timeSinceLastKey(keystroke.getTimeSinceLastKey())
                        .build()
                        .orElseThrow()
                )
                .collect(Collectors.toList());
    }

    public static ConversationModel toModel(final Conversation dto) {
        final ConversationModel model = new ConversationModel();
        model.setId(dto.getId());
        DateService.getTimestamp(dto.getCreated())
                .ifPresent(model::setDateAdded);

        DateService.getTimestamp(dto.getDeactivated())
                .ifPresent(model::setDateDeactivated);

        return model;
    }
}
