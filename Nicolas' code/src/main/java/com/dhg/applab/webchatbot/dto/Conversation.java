package com.dhg.applab.webchatbot.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Optional;

@Data
public class Conversation {
    private Long pk;
    private Integer id;
    private OffsetDateTime created;
    private OffsetDateTime deactivated;


    private Conversation(Long pk, Integer id, OffsetDateTime created, OffsetDateTime deactivated) {
        this.pk = pk;
        this.id = id;
        this.created = created;
        this.deactivated = deactivated;
    }

    public com.dhg.applab.webchatbot.dao.Conversation toDao() {
        return new com.dhg.applab.webchatbot.dao.Conversation(
                pk,
                id,
                created,
                deactivated
        );
    }

    public static class ConversationBuilder {
        private Long pk;
        private Integer id;
        private OffsetDateTime created;
        private OffsetDateTime deactivated;
        private boolean allowed;

        public static ConversationBuilder from(final Conversation dto) {
            return new ConversationBuilder()
                    .pk(dto.getPk())
                    .id(dto.getId())
                    .created(dto.getCreated())
                    .deactivated(dto.getDeactivated());
        }

        public static ConversationBuilder from(final com.dhg.applab.webchatbot.dao.Conversation dao) {
            return new ConversationBuilder()
                    .pk(dao.getPk())
                    .id(dao.getId())
                    .created(dao.getCreated())
                    .deactivated(dao.getDeactivated());
        }

        private ConversationBuilder pk(final Long pk) {
            this.pk = pk;
            return this;
        }

        public ConversationBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        public ConversationBuilder created(final OffsetDateTime created) {
            this.created = created;
            return this;
        }

        public ConversationBuilder deactivated(final OffsetDateTime deactivated) {
            this.deactivated = deactivated;
            return this;
        }

        public ConversationBuilder allowed(final boolean allowed) {
            this.allowed = allowed;
            return this;
        }

        public Optional<Conversation> build() {
            return Optional.of(
                    new Conversation(pk, id, created, deactivated)
            );
        }
    }
}

