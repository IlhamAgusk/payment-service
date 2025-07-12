package com.nova.service;

import com.nova.model.Customer;
import com.nova.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.listAll();
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findByIdOptional(id);
    }

    @Transactional
    public Customer create(Customer customer) {
        customerRepository.persist(customer);
        return customer;
    }

    @Transactional
    public boolean update(String id, Customer updatedCustomer) {
        return customerRepository.findByIdOptional(id).map(existing -> {
            existing.setName(updatedCustomer.getName());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setPhone(updatedCustomer.getPhone());
            return true;
        }).orElse(false);
    }

    @Transactional
    public boolean delete(String id) {
        return customerRepository.deleteById(id);
    }
}
