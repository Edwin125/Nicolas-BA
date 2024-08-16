package com.dhg.applab.webchatbot.repository;

import com.dhg.applab.webchatbot.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageRepository {
    @Autowired
    private JpaMessageRepository jpaMessageRepository;

    public List<Message> getByConversationId(final Integer id) {
        return jpaMessageRepository.findByConversationId(id)
                .stream()
                .map(Message.MessageBuilder::fromDao)
                .map(Message.MessageBuilder::build)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public Optional<Message> save(final Message dto) {
        return Optional.of(dto)
                .map(Message::toDao)
                .map(jpaMessageRepository::save)
                .map(Message.MessageBuilder::fromDao)
                .flatMap(Message.MessageBuilder::build);
    }
}
