package com.dhg.applab.webchatbot.model;

import lombok.Data;

@Data
public class ChatMessageParams {
    private String text;
    private boolean user;
}
