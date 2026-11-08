package ru.demo.kafka.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.Instant;

public final class MessageTransformer {
    private MessageTransformer() { }

    public static String enrich(String json, ObjectMapper objectMapper) {
        try {
            ObjectNode event = (ObjectNode) objectMapper.readTree(json);
            String message = event.path("message").asText("");
            event.put("uppercaseMessage", message.toUpperCase());
            event.put("processedAt", Instant.now().toString());
            return objectMapper.writeValueAsString(event);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Некорректное JSON-сообщение в Kafka", exception);
        }
    }
}
