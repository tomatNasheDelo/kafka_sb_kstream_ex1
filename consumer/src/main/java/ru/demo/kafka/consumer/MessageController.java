package ru.demo.kafka.consumer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${app.frontend-origin}")
public class MessageController {
    private final MessageStore messageStore;

    public MessageController(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @GetMapping("/api/messages")
    public List<ProcessedMessage> messages() {
        return messageStore.newestFirst();
    }
}
