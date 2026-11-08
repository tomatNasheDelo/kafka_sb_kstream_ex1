package ru.demo.kafka.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageStreamTopology {
    @Bean
    KStream<String, String> messageStream(
            StreamsBuilder streamsBuilder,
            ObjectMapper objectMapper,
            @Value("${app.kafka.input-topic}") String inputTopic,
            @Value("${app.kafka.output-topic}") String outputTopic) {
        KStream<String, String> stream = streamsBuilder.stream(
                inputTopic, Consumed.with(Serdes.String(), Serdes.String()));
        stream.mapValues(value -> MessageTransformer.enrich(value, objectMapper))
                .to(outputTopic);
        return stream;
    }
}
