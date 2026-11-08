package ru.demo.kafka.producer;

import java.time.Instant;

public record MessageEvent(String id, String message, Instant createdAt) { }
