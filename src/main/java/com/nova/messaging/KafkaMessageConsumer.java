package com.nova.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaMessageConsumer {

    private static final Logger LOGGER = Logger.getLogger(KafkaMessageConsumer.class);

    @Inject
    ObjectMapper objectMapper;

    @Incoming("my-kafka-topic")
    public void consume(String jsonMessage) {
        try {
            MessagePayload payload = objectMapper.readValue(jsonMessage, MessagePayload.class);
            LOGGER.infof("📥 Received Kafka JSON: %s", payload.message);

            // Further processing here
        } catch (Exception e) {
            LOGGER.error("❌ Failed to deserialize Kafka message", e);
        }
    }
}
