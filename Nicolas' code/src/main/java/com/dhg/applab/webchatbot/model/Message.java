package com.dhg.applab.webchatbot.model;

import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.MessageType;

import java.util.List;
import java.util.Map;

public class Message implements org.springframework.ai.chat.messages.Message {
    
    @Override
    public String getContent() {
        return null;
    }

    @Override
    public List<Media> getMedia() {
        return null;
    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return null;
    }
}
