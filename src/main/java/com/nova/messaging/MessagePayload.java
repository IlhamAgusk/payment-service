package com.nova.messaging;

public class MessagePayload {
    public String message;

    public MessagePayload() {} // Required for deserialization

    public MessagePayload(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessagePayload{message='" + message + "'}";
    }
}
