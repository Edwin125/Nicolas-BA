package com.dhg.applab.webchatbot.dao;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    @NaturalId
    @Column(nullable = false)
    private Integer id;
    @Column(name = "created")
    private OffsetDateTime created;
    @Column(name = "deactivated")
    private OffsetDateTime deactivated;

    protected Conversation() {

    }

    public Conversation(Long pk, Integer id, OffsetDateTime created, OffsetDateTime deactivated) {
        this.pk = pk;
        this.id = id;
        this.created = created;
        this.deactivated = deactivated;
    }
}
