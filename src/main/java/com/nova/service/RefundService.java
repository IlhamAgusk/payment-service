package com.nova.service;

import com.nova.model.Payment;
import com.nova.model.Refund;
import com.nova.repository.RefundRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RefundService {

    @Inject
    RefundRepository refundRepository;

    @Transactional
    public Refund createRefund(Payment payment, BigDecimal amount, String reason) {
        Refund refund = new Refund();
        refund.setPayment(payment);
        refund.setAmount(amount);
        refund.setReason(reason);
        refund.setStatus("PENDING");

        refundRepository.persist(refund);
        return refund;
    }

    public List<Refund> getAll() {
        return refundRepository.listAll();
    }

    public Optional<Refund> getById(String id) {
        return refundRepository.findByIdOptional(id);
    }

    @Transactional
    public boolean deleteById(String id) {
        return refundRepository.deleteById(id);
    }

    public Refund getByPaymentId(String paymentId) {
        return refundRepository.findByPaymentId(paymentId);
    }
}
