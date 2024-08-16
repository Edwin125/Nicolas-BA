package com.dhg.applab.webchatbot;

import com.dhg.applab.webchatbot.dto.Conversation;
import com.dhg.applab.webchatbot.mapper.ConversationMapper;
import com.dhg.applab.webchatbot.model.ChatMessageModel;
import com.dhg.applab.webchatbot.model.ChatMessageParams;
import com.dhg.applab.webchatbot.model.ChatMessageSendParams;
import com.dhg.applab.webchatbot.repository.ConversationRepository;
import com.dhg.applab.webchatbot.repository.MessageRepository;
import com.dhg.applab.webchatbot.utils.DateService;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/ai")
public class MainController {
    final String AI_PROMPT = "Hey, let's have a chat. I'm an elderly person that likes to talk."
            + " You have to induce as much response from me as possible."
            + " Your next message should EXACTLY be 'Hello, how are you?'";

    final String AI_PROMPT_DE = "Hey, lass uns ein bisschen plaudern. Ich bin ein älterer Mensch, der gerne redet."
            + " Sie müssen so viele Antwort wie möglich von mir hervorrufen."
            + " Ihre nächste Nachricht sollte GENAU so lauten: 'Hallo, wie geht es Ihnen?'";

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private OllamaChatClient chatClient;

    @GetMapping("/init/{id}")
    public @ResponseBody List<ChatMessageModel> initChat(@PathVariable final String id) {
        final Conversation conversation = Optional.of(id)
                .map(Integer::parseInt)
                .flatMap(conversationRepository::getById)
                .orElseGet(() -> createNewConversation(id).orElseThrow());

        return Optional.of(conversation.getId())
                .map(messageRepository::getByConversationId)
                .orElseThrow()
                .stream()
                .map(this::toModel)
                .sorted(Comparator.comparing(ChatMessageModel::getTime))
                .collect(Collectors.toList());
    }

    private Optional<Conversation> createNewConversation(final String id) {
        final String aiResponse = chatClient.call(new Prompt(new UserMessage(AI_PROMPT_DE)))
                .getResults()
                .stream()
                .findFirst()
                .orElseThrow()
                .getOutput()
                .getContent();

        final Optional<Conversation> conversation = Optional.of(id)
                .map(Integer::parseInt)
                .map(ConversationMapper::createNewConversation)
                .flatMap(conversationRepository::save);

        conversation.flatMap(conv -> new com.dhg.applab.webchatbot.dto.Message.MessageBuilder()
                        .created(OffsetDateTime.now())
                        .conversation(conv)
                        .user(false)
                        .text(aiResponse)
                        .build()
                )
                .map(messageRepository::save);

        return conversation;
    }

    private ChatMessageModel toModel(final com.dhg.applab.webchatbot.dto.Message message) {
        final ChatMessageModel model = new ChatMessageModel();
        model.setText(message.getText());
        model.setUser(message.isUser());
        model.setTime(DateService.getTimestamp(message.getCreated()).orElseThrow());
        return model;
    }

    @PostMapping("/send")
    public @ResponseBody ChatMessageModel generate(@Validated @RequestBody ChatMessageSendParams params) {
        final List<Message> messages = new ArrayList<>();

        messages.add(new UserMessage(AI_PROMPT_DE));

        messages.addAll(
                params.getChatHistory()
                        .stream()
                        .map(this::toMessage)
                        .toList()
        );

        Optional.of(params.getChat())
                .map(this::toMessage)
                .ifPresent(messages::add);

        final String aiResponse = chatClient.call(new Prompt(messages))
                .getResults()
                .stream()
                .findFirst()
                .orElseThrow()
                .getOutput()
                .getContent();

        final Optional<Conversation> conversation = conversationRepository.getById(Integer.valueOf(params.getId()));

        conversation.flatMap(conv -> {
                    final ChatMessageParams chat = params.getChat();

                    return new com.dhg.applab.webchatbot.dto.Message.MessageBuilder()
                            .created(OffsetDateTime.now())
                            .conversation(conv)
                            .user(true)
                            .text(chat.getText())
                            .keystrokes(ConversationMapper.translateKeystroke(params.getKeystrokes()))
                            .build();
                })
                .map(messageRepository::save);

        conversation.flatMap(conv -> new com.dhg.applab.webchatbot.dto.Message.MessageBuilder()
                        .created(OffsetDateTime.now())
                        .conversation(conv)
                        .user(false)
                        .text(aiResponse)
                        .build())
                .map(messageRepository::save);

        return toChatMessageModelAi(aiResponse);
    }

    private Message toMessage(final ChatMessageParams chat) {
        if (chat.isUser()) {
            return new UserMessage(chat.getText());
        } else {
            return new AssistantMessage(chat.getText());
        }
    }

    private ChatMessageModel toChatMessageModelAi(final String text) {
        final ChatMessageModel model = new ChatMessageModel();

        model.setText(text);
        model.setUser(false);
        model.setTime(DateService.getTimestamp(OffsetDateTime.now()).orElseThrow());

        return model;
    }
}