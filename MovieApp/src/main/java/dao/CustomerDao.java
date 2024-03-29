package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Customer;
import model.Movie;
import model.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object (DAO) class for handling customer-related database operations.
 */
@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all customers from the database.
     *
     * @return List of all customers.
     */
    public List<Customer> getCustomers() {
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    /**
     * Inserts a new customer into the database or updates an existing one.
     *
     * @param customer The customer object to insert or update.
     * @return The inserted or updated customer object.
     */
    public Customer insertCustomer(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            em.merge(customer);
        }
        return customer;
    }

    /**
     * Retrieves a customer from the database by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The customer with the specified ID.
     */
    public Customer getCustomerById(Long id) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.id = :id", Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * Deletes a customer from the database by their ID.
     *
     * @param id The ID of the customer to delete.
     */
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }

    /**
     * Retrieves a list of movies watched by a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return List of movies watched by the customer.
     */
    public List<Movie> getWatchedMovies(Long customerId) {
        Customer customer = getCustomerById(customerId);
        return customer.getHistory().stream()
                .map(Session::getMovie)
                .toList();
    }
}
