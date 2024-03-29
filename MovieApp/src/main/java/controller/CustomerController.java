package controller;

import dao.CustomerDao;
import dao.MovieDao;
import model.Customer;
import model.Genre;
import model.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerDao customerDao;
    private final MovieDao movieDao;

    /**
     * Constructor for CustomerController.
     * @param customerDao Instance of CustomerDao.
     * @param movieDao Instance of MovieDao.
     */
    public CustomerController(CustomerDao customerDao, MovieDao movieDao) {
        this.customerDao = customerDao;
        this.movieDao = movieDao;
    }

    /**
     * Retrieves all customers.
     * @return List of all customers.
     */
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerDao.getCustomers();
    }

    /**
     * Retrieves a customer by ID.
     * @param id The ID of the customer to retrieve.
     * @return The customer with the specified ID.
     */
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerDao.getCustomerById(id);
    }

    /**
     * Creates a new customer.
     * @param customer The customer object to create.
     * @return The created customer object.
     */
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerDao.insertCustomer(customer);
    }

    /**
     * Deletes a customer by ID.
     * @param id The ID of the customer to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerDao.deleteCustomer(id);
    }

    /**
     * Retrieves recommended movies for a customer.
     * @param customerId The ID of the customer to retrieve recommendations for.
     * @return List of recommended movies.
     */
    @GetMapping("/{customerId}/recommendations")
    public List<Movie> getRecommendedMovies(@PathVariable Long customerId) {
        List<Movie> watchedMovies = customerDao.getWatchedMovies(customerId);
        List<Movie> weekMovies = movieDao.getWeekMovies();
        Set<Movie> recommendedMovies = new HashSet<>();

        for(Movie weekMovie: weekMovies){
            for (Movie watchedMovie : watchedMovies){
                for(Genre genre: weekMovie.getGenres()){
                    if(watchedMovie.getGenres().contains(genre)
                            || weekMovie.getAgeRestriction().equals(watchedMovie.getAgeRestriction())){
                        recommendedMovies.add(weekMovie);
                        break;
                    }
                }
            }
        }

        return new ArrayList<>(recommendedMovies);
    }
}
