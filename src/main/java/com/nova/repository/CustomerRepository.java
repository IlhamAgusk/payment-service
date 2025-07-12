package com.nova.repository;

import com.nova.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, String> {

    public Optional<Customer> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<Customer> findByPhone(String phone) {
        return find("phone", phone).firstResultOptional();
    }
}