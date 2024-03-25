package controller;

import dao.CustomerDao;
import model.Customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerDao customerDao;

    CustomerController(CustomerDao customerDao){this.customerDao = customerDao;}

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerDao.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerDao.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerDao.insertCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerDao.deleteCustomer(id);
    }
}
