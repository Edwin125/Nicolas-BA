package com.dhg.applab.webchatbot.dao;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(
        name = "message",
        uniqueConstraints = @UniqueConstraint(columnNames = "pk", name = "message_pk")
)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    @Column(name = "created")
    private OffsetDateTime created;
    @ManyToOne(targetEntity = Conversation.class)
    @JoinColumn(
            name = "conversation_pk",
            columnDefinition = "BIGINT",
            referencedColumnName = "pk",
            nullable = false,
            foreignKey = @ForeignKey(name = "message_conversation_fk1")
    )
    private Conversation conversation;
    @Column(name = "text", length = 1000)
    private String text;
    @Column(name = "\"user\"")
    private boolean user;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Keystroke> keystrokes;

    protected Message() {

    }

    public Message(
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
}
