package controller;

import dao.MovieDao;
import jakarta.validation.Valid;
import model.Movie;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieDao movieDao;

    /**
     * Constructor for MovieController.
     * @param movieDao Instance of MovieDao.
     */
    public MovieController(MovieDao movieDao){
        this.movieDao = movieDao;
    }

    /**
     * Retrieves movies for the current week.
     * @return List of movies for the current week.
     */
    @GetMapping
    public List<Movie> getWeekMovies() {
        return movieDao.getWeekMovies();
    }

    /**
     * Searches movies based on provided filters.
     * @param filters Map of filters.
     * @return List of movies matching the filters.
     */
    @GetMapping("/search")
    public List<Movie> searchMoviesByFilters(@RequestParam Map<String, String> filters) {
        return movieDao.searchMoviesByFilters(filters);
    }

    /**
     * Retrieves a movie by its ID.
     * @param id The ID of the movie to retrieve.
     * @return The movie with the specified ID.
     */
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieDao.getMovieById(id);
    }

    /**
     * Creates a new movie.
     * @param movie The movie object to create.
     * @return The created movie object.
     */
    @Transactional
    @PostMapping("/create")
    public Movie createMovie(@RequestBody @Valid Movie movie){
        return movieDao.insertMovie(movie);
    }

    /**
     * Deletes a movie by its ID.
     * @param id The ID of the movie to delete.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieDao.deleteMovie(id);
    }
}
