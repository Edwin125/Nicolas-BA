package com.dhg.applab.webchatbot.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Message {
    private Long pk;
    private OffsetDateTime created;
    private Conversation conversation;
    private String text;
    private boolean user;
    private List<Keystroke> keystrokes;


    private Message(
            Long pk,
            OffsetDateTime created,
            Conversation conversation,
            String text,
            boolean user,
            List<Keystroke> keystrokes
    ) {
        this.pk = pk;
        this.created = created;
        this.conversation = conversation;
        this.text = text;
        this.user = user;
        this.keystrokes = keystrokes;
    }

    public com.dhg.applab.webchatbot.dao.Message toDao() {
        return new com.dhg.applab.webchatbot.dao.Message(
                pk,
                created,
                conversation.toDao(),
                text,
                user,
                keystrokes.stream()
                        .map(Keystroke::toDao)
                        .collect(Collectors.toList())
        );
    }

    public static class MessageBuilder {
        private Long pk;
        private OffsetDateTime created;
        private Conversation conversation;
        private String text;
        private boolean user;
        private List<Keystroke> keystrokes;

        public static MessageBuilder from(final Message dto) {
            return new MessageBuilder()
                    .pk(dto.getPk())
                    .created(dto.getCreated())
                    .conversation(dto.getConversation())
                    .text(dto.getText())
                    .user(dto.isUser())
                    .keystrokes(dto.getKeystrokes());
        }

        public static MessageBuilder fromDao(final com.dhg.applab.webchatbot.dao.Message dao) {
            return new MessageBuilder()
                    .pk(dao.getPk())
                    .created(dao.getCreated())
                    .conversation(Conversation.ConversationBuilder.from(dao.getConversation())
                            .build()
                            .orElseThrow()
                    )
                    .text(dao.getText())
                    .user(dao.isUser());
        }

        private MessageBuilder pk(Long pk) {
            this.pk = pk;
            return this;
        }

        public MessageBuilder created(OffsetDateTime created) {
            this.created = created;
            return this;
        }

        public MessageBuilder conversation(Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public MessageBuilder text(String text) {
            this.text = text;
            return this;
        }

        public MessageBuilder user(boolean user) {
            this.user = user;
            return this;
        }

        public MessageBuilder keystrokes(List<Keystroke> keystrokes) {
            this.keystrokes = keystrokes;
            return this;
        }

        public Optional<Message> build() {
            if (keystrokes == null) {
                this.keystrokes = new ArrayList<>();
            }
            return Optional.of(
                    new Message(pk, created, conversation, text, user, keystrokes)
            );
        }
    }
}

