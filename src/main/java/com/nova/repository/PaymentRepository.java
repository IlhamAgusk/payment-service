package com.nova.repository;

import com.nova.model.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PaymentRepository implements PanacheRepositoryBase <Payment,String> {

    public Optional<Payment> findByIdStatus(String status) {
        return find("status", status).firstResultOptional();
    }

    public boolean existsById(String id) {
        return findByIdOptional(id).isPresent();
    }
}
