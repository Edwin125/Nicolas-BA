package com.dhg.applab.webchatbot.repository;

import com.dhg.applab.webchatbot.dto.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationRepository {
    @Autowired
    private JpaConversationRepository jpaConversationRepository;

    public Optional<Conversation> getById(final Integer id) {
        return jpaConversationRepository.findById(id)
                .map(Conversation.ConversationBuilder::from)
                .flatMap(Conversation.ConversationBuilder::build);
    }

    public Optional<Conversation> save(final Conversation dto) {
        return Optional.of(dto)
                .map(Conversation::toDao)
                .map(jpaConversationRepository::save)
                .map(Conversation.ConversationBuilder::from)
                .flatMap(Conversation.ConversationBuilder::build);
    }

    public List<Conversation> findAll() {
        return jpaConversationRepository.findAll()
                .stream()
                .map(Conversation.ConversationBuilder::from)
                .map(Conversation.ConversationBuilder::build)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
