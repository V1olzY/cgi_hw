package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager em;

    public List<Customer> getCustomers() {
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    public Customer insertCustomer(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            em.merge(customer);
            return customer;
        }
        return customer;
    }

    public Customer getCustomerById(Long id) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.id = :id", Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }
}
