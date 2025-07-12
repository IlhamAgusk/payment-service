package com.nova.controller;

import com.nova.dto.CustomerDTO;
import com.nova.dto.PaymentDTO;
import com.nova.dto.RefundDTO;
import com.nova.model.Customer;
import com.nova.model.Payment;
import com.nova.model.Refund;
import com.nova.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerService customerService;

    @GET
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Transactional
    public Response create(Customer customer) {
        Customer created = customerService.create(customer);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") String id, Customer customer) {
        boolean updated = customerService.update(id, customer);
        return updated
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") String id) {
        boolean deleted = customerService.delete(id);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    // ✅ GET customer details (with payments and refunds)
    @GET
    @Path("/{id}/details")
    public Response getCustomerDetails(@PathParam("id") String id) {
        Optional<Customer> customerOpt = customerService.findById(id);
        if (customerOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Customer customer = customerOpt.get();

        CustomerDTO dto = new CustomerDTO();
        dto.id = customer.getId();
        dto.name = customer.getName();
        dto.email = customer.getEmail();
        dto.phone = customer.getPhone();

        dto.payments = customer.getPayments().stream().map(payment -> {
            PaymentDTO pDto = new PaymentDTO();
            pDto.id = payment.getId();
            pDto.amount = payment.getAmount();
            pDto.paymentMethod = payment.getPaymentMethod();
            pDto.status = payment.getStatus();

            if (payment.getRefund() != null) {
                Refund r = payment.getRefund();
                RefundDTO rDto = new RefundDTO();
                rDto.id = r.getId();
                rDto.amount = r.getAmount();
                rDto.reason = r.getReason();
                rDto.status = r.getStatus();
                pDto.refund = rDto;
            }

            return pDto;
        }).toList();

        return Response.ok(dto).build();
    }
}