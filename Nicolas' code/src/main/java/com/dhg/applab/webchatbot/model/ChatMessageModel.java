package com.dhg.applab.webchatbot.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatMessageModel {
    private String text;
    private boolean user;
    private Timestamp time;
}
