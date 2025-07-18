package com.nova.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaProducerService {

    @Channel("my-producer")
    Emitter<String> emitter;

    @Inject
    ObjectMapper objectMapper;

    public void sendMessage(String message) {
        try {
            MessagePayload payload = new MessagePayload(message);
            String json = objectMapper.writeValueAsString(payload);
            emitter.send(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize message", e);
        }
    }
}
