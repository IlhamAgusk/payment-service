package com.nova.service;

import com.nova.model.Payment;
import com.nova.repository.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    public List<Payment> findAll() {
        return paymentRepository.listAll();
    }

    public Optional<Payment> findById(String id) {
        return paymentRepository.findByIdOptional(id);
    }

    public Optional<Payment> findByStatus(String status) {
        return paymentRepository.findByIdStatus(status);
    }

    @Transactional
    public Payment create(Payment payment) {
        paymentRepository.persist(payment);
        return payment;
    }

    @Transactional
    public boolean update(String id, Payment updatePayment) {
        return paymentRepository.findByIdOptional(id).map(payment -> {
            payment.setStatus(updatePayment.getStatus());
            payment.setAmount(updatePayment.getAmount());
            payment.setPaymentMethod(updatePayment.getPaymentMethod());//bni
            return true;
        }).orElse(false);
    }

    public boolean delete(String id) {
        return paymentRepository.deleteById(id);
    }
}