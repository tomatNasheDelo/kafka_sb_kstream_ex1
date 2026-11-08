package ru.demo.kafka.consumer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class MessageStore {
    private static final int MAX_MESSAGES = 100;
    private final ConcurrentLinkedDeque<ProcessedMessage> messages = new ConcurrentLinkedDeque<>();

    public void add(ProcessedMessage message) {
        messages.addFirst(message);
        while (messages.size() > MAX_MESSAGES) {
            messages.pollLast();
        }
    }

    public List<ProcessedMessage> newestFirst() {
        return List.copyOf(messages);
    }
}
