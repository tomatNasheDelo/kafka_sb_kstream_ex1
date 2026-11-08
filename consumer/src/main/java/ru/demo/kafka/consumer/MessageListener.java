package ru.demo.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private final ObjectMapper objectMapper;
    private final MessageStore messageStore;

    public MessageListener(ObjectMapper objectMapper, MessageStore messageStore) {
        this.objectMapper = objectMapper;
        this.messageStore = messageStore;
    }

    @KafkaListener(topics = "${app.kafka.output-topic}")
    public void listen(String json) {
        try {
            ProcessedMessage message = objectMapper.readValue(json, ProcessedMessage.class);
            messageStore.add(message);
            System.out.printf("Получено из Kafka Streams: %s (uppercase: %s)%n",
                    message.message(), message.uppercaseMessage());
        } catch (Exception exception) {
            System.err.println("Не удалось обработать сообщение из Kafka: " + exception.getMessage());
        }
    }
}
