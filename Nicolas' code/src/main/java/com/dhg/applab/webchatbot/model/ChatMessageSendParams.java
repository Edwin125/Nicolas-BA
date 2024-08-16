package com.dhg.applab.webchatbot.model;

import jakarta.annotation.Nonnull;
import lombok.Data;

import java.util.List;

@Data
public class ChatMessageSendParams {
    @Nonnull
    private String id;
    @Nonnull
    private List<ChatMessageParams> chatHistory;
    @Nonnull
    private ChatMessageParams chat;
    @Nonnull
    private List<KeystrokeParams> keystrokes;
}
