package ru.demo.kafka.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MessageController.class)
class MessageControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean MessagePublisher messagePublisher;

    @Test
    void validPostPublishesMessage() throws Exception {
        mockMvc.perform(post("/messages").param("message", "Привет Kafka Streams"))
                .andExpect(status().isOk()).andExpect(view().name("sent"));
        verify(messagePublisher).publish("Привет Kafka Streams");
    }

    @Test
    void blankPostDoesNotPublish() throws Exception {
        mockMvc.perform(post("/messages").param("message", "  "))
                .andExpect(status().isOk()).andExpect(view().name("index"));
        verifyNoInteractions(messagePublisher);
    }
}
