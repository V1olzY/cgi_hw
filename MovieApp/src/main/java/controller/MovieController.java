package controller;


import dao.MovieDao;
import jakarta.validation.Valid;
import model.Movie;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieDao movieDao;

    public MovieController(MovieDao movieDao){
        this.movieDao = movieDao;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieDao.getMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieDao.getMovieById(id);
    }


    @Transactional
    @PostMapping
    public Movie createMovie(@RequestBody @Valid Movie movie){
        return movieDao.insertMovie(movie);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieDao.deleteMovie(id);
    }


}
