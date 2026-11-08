package ru.demo.kafka.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MessageTransformerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addsUppercaseMessageAndProcessingTimestamp() throws Exception {
        String result = MessageTransformer.enrich(
                "{\"id\":\"1\",\"message\":\"hello\",\"createdAt\":\"2025-01-01T00:00:00Z\"}", objectMapper);
        var json = objectMapper.readTree(result);

        assertEquals("HELLO", json.get("uppercaseMessage").asText());
        assertFalse(json.get("processedAt").asText().isBlank());
    }
}
