package com.dhg.applab.webchatbot.model;

import lombok.Getter;

@Getter
public enum ChatMessageUserEnum {
    USER("user"),
    ASSISTANT("assistant");

    private final String description;

    ChatMessageUserEnum(final String description) {
        this.description = description;
    }
}
