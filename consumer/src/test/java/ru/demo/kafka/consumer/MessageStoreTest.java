package ru.demo.kafka.consumer;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageStoreTest {
    @Test
    void keepsNewestMessagesFirstAndLimitsHistory() {
        MessageStore store = new MessageStore();
        for (int i = 0; i < 105; i++) {
            store.add(new ProcessedMessage(String.valueOf(i), "message " + i, Instant.now(),
                    "MESSAGE " + i, Instant.now()));
        }

        assertEquals(100, store.newestFirst().size());
        assertEquals("104", store.newestFirst().get(0).id());
    }
}
