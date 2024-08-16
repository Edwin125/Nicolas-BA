package com.dhg.applab.webchatbot.repository;

import com.dhg.applab.webchatbot.dao.Conversation;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaConversationRepository extends JpaRepository<Conversation, Integer> {
    @Nonnull
    @Query("SELECT e from Conversation e where e.id = :id")
    Optional<Conversation> findById(@Nonnull @Param("id") final Integer id);
}

