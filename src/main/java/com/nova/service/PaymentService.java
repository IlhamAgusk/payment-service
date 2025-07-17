package com.nova.service;

import com.nova.model.Customer;
import com.nova.model.Payment;
import com.nova.repository.CustomerRepository;
import com.nova.repository.PaymentRepository;
import com.github.pjfanning.xlsx.StreamingReader;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
//import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    CustomerRepository customerRepository;

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
            payment.setPaymentMethod(updatePayment.getPaymentMethod());
            return true;
        }).orElse(false);
    }

    public boolean delete(String id) {
        return paymentRepository.deleteById(id);
    }

    @Transactional
    public void importFromExcel(InputStream inputStream) {
        try (Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(4096)
                .open(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean skipHeader = true;

            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                Cell amountCell = row.getCell(0);         // amount
                Cell customerIdCell = row.getCell(1);     // customer_id
                Cell methodCell = row.getCell(2);         // payment_method
                Cell statusCell = row.getCell(3);         // status

                if (amountCell == null || customerIdCell == null) {
                    System.err.println("Missing required fields");
                    continue;
                }

                try {
                    BigDecimal amount = BigDecimal.valueOf(amountCell.getNumericCellValue());
                    String customerIdStr = customerIdCell.getStringCellValue().trim();

                    String paymentMethod = methodCell != null ? methodCell.getStringCellValue().trim() : "EXCEL";
                    String status = statusCell != null ? statusCell.getStringCellValue().trim() : "PENDING";

                    Optional<Customer> customerOpt = customerRepository.findByIdOptional(customerIdStr);
                    if (customerOpt.isEmpty()) {
                        System.err.println("Customer not found for ID: " + customerIdStr);
                        continue;
                    }

                    Customer customer = customerOpt.get();

                    Payment payment = new Payment();
                    payment.setAmount(amount);
                    payment.setCustomer(customer);
                    payment.setPaymentMethod(paymentMethod);
                    payment.setStatus(status);

                    paymentRepository.persist(payment);
                    System.out.println("Inserted payment for customer: " + customer.getId());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println("Skipping row due to error: " + ex.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
    }
}