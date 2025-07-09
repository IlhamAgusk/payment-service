package com.nova.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    public BigDecimal amount;

    @Column(name = "payment_method", nullable = false)
    public String paymentMethod;

    @Column(nullable = false)
    public String status;

    //Getter & Setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
