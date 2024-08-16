package com.dhg.applab.webchatbot;

import com.dhg.applab.webchatbot.dto.Conversation;
import com.dhg.applab.webchatbot.mapper.ConversationMapper;
import com.dhg.applab.webchatbot.model.ConversationModel;
import com.dhg.applab.webchatbot.repository.ConversationRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = "/request/admin")
public class AdminController {
    @Autowired
    private ConversationRepository conversationRepository;

    @PostMapping(value = "/login")
    public @ResponseBody String login(@RequestParam final String password) {
        final String token = "fake-token";
        if (Objects.equals(password, "12345678")) {
            return token;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/conversation/all")
    public @ResponseBody List<ConversationModel> getConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(ConversationMapper::toModel)
                .toList();
    }

    @PostMapping(value = "/conversation/create/{id}")
    public @ResponseBody Conversation createConversation(@PathVariable @Nonnull final String id) {

        return createNewConversation(id);
    }

    private Conversation createNewConversation(final String id) {
        return Optional.of(id)
                .map(Integer::parseInt)
                .map(idParsed -> new Conversation.ConversationBuilder()
                        .created(OffsetDateTime.now())
                        .id(idParsed)
                        .allowed(true)
                        .build()
                        .orElseThrow())
                .flatMap(conversationRepository::save)
                .orElseThrow();
    }

    @PostMapping(value = "/conversation/toggle/{id}")
    public @ResponseBody Conversation toggleConversation(@PathVariable @Nonnull final String id) {
        final Optional<Conversation> conversation = Optional.of(id)
                .map(Integer::parseInt)
                .flatMap(conversationRepository::getById)
                .map(dao -> {
                    final Conversation dto = Conversation.ConversationBuilder.from(dao)
                            .build()
                            .orElseThrow();

                    dto.setDeactivated(dto.getDeactivated() == null ? OffsetDateTime.now() : null);
                    return dto;
                })
                .flatMap(conversationRepository::save);

        return conversation.orElseThrow();
    }
}
