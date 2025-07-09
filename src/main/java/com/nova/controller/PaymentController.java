package com.nova.controller;

import com.nova.model.Payment;
import com.nova.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {

    private final PaymentService paymentService;

    @Inject
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GET
    public List<Payment> getAll() {
        return paymentService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        return paymentService.findById(id)
                .map(payment -> Response.ok(payment).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());//bni222
    }

    @POST
    public Response create(Payment payment) {
        Payment created = paymentService.create(payment);
        return Response.status(Response.Status.CREATED).entity(created).build();//bniiiiiiiii
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, Payment payment) {
        boolean updated = paymentService.update(id, payment);
        return updated ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        boolean deleted = paymentService.delete(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}