package com.nova.messaging;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/kafka")
public class KafkaResource {

    @Inject
    KafkaProducerService producer;

    // Inner class model untuk JSON payload
    public static class MessageRequest {
        public String message;
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendMessage(MessageRequest request) {
        if (request == null || request.message == null || request.message.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Message is required").build();
        }

        producer.sendMessage(request.message);
        return Response.ok("Message sent: " + request.message).build();
    }
}
