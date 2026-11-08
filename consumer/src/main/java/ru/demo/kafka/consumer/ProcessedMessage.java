package ru.demo.kafka.consumer;

import java.time.Instant;

public record ProcessedMessage(
        String id,
        String message,
        Instant createdAt,
        String uppercaseMessage,
        Instant processedAt) { }
