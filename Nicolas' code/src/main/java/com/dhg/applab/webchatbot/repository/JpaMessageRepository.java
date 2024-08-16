package com.dhg.applab.webchatbot.repository;

import com.dhg.applab.webchatbot.dao.Message;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface JpaMessageRepository extends CrudRepository<Message, Integer> {
    @Nonnull
    @Query("SELECT e from Message e where e.conversation.id = :id")
    Collection<Message> findByConversationId(@Nonnull @Param("id") final Integer id);
}

