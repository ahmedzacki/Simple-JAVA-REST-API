package com.ahmed;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age) {

        // will have some staff
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (request.name != null) {
            customer.setName(request.name);
        } else if (request.email != null) {
            customer.setEmail(request.email);
        } else if (request.age != null) {
            customer.setAge(request.age);
        }
        customerRepository.save(customer);
    }
}
