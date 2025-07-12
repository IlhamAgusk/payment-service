package com.nova.dto;

import java.math.BigDecimal;

public class PaymentDTO {
    public String id;
    public BigDecimal amount;
    public String paymentMethod;
    public String status;
    public RefundDTO refund;
}
