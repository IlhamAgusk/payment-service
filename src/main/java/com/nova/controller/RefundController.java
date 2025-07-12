package com.nova.controller;

import com.nova.model.Payment;
import com.nova.model.Refund;
import com.nova.service.RefundService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Path("/api/refunds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefundController {

    @Inject
    RefundService refundService;

    // Example DTO for request
    public static class RefundRequest {
        public String paymentId;
        public BigDecimal amount;
        public String reason;
    }

    @POST
    @Transactional
    public Response create(RefundRequest request) {
        // Normally you would load payment from DB first, this is a dummy
        Payment payment = new Payment();
        payment.setId(request.paymentId);

        Refund refund = refundService.createRefund(payment, request.amount, request.reason);
        return Response.ok(refund).status(Response.Status.CREATED).build();
    }

    @GET
    public List<Refund> getAll() {
        return refundService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        Optional<Refund> refund = refundService.getById(id);
        return refund.map(Response::ok)
                     .orElse(Response.status(Response.Status.NOT_FOUND))
                     .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") String id) {
        boolean deleted = refundService.deleteById(id);
        return deleted ? Response.noContent().build()
                       : Response.status(Response.Status.NOT_FOUND).build();
    }
}
