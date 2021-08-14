package com.srlite.srlitespringservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.srlite.srlitespringserver.error.ResourceNotFoundException;
import com.srlite.srlitespringservice.model.Customer;
import com.srlite.srlitespringservice.repository.CustomerRepository;

import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    
    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllEmployees() {
        List<Customer> list =  customerRepo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    
    
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getEmployeeById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        Customer employee = customerRepo.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createEmployee(@RequestBody Customer customer) {
        
        final Customer newCustomer = customerRepo.save((Customer) customer);
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping(path="/customers/{id}")
    public ResponseEntity<Customer> updateEmployee(@PathVariable Long id,
         @RequestBody Customer details) throws ResourceNotFoundException {
        Customer customer = customerRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));

        customer.setAge(details.getAge());
        customer.setEmail(details.getEmail());
        customer.setName(details.getName());
        final Customer updated = customerRepo.save(customer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/customers/del/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable Long id)
         throws ResourceNotFoundException {
        Customer customer = customerRepo.findById(id)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        customerRepo.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
