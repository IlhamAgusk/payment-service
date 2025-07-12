package com.nova.repository;

import com.nova.model.Refund;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefundRepository implements PanacheRepositoryBase<Refund, String> {
    // Custom query examples (optional)
    public Refund findByPaymentId(String paymentId) {
        return find("payment.id", paymentId).firstResult();
    }
}
