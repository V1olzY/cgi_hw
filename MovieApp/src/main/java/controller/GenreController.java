package controller;

import dao.GenreDao;
import model.Genre;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreDao genreDao;

    /**
     * Constructor for GenreController.
     * @param genreDao Instance of GenreDao.
     */
    public GenreController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    /**
     * Retrieves all genres.
     * @return List of all genres.
     */
    @GetMapping
    public List<Genre> getAllGenres() {
        return genreDao.getGenres();
    }

    /**
     * Retrieves a genre by ID.
     * @param id The ID of the genre to retrieve.
     * @return The genre with the specified ID.
     */
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        return genreDao.getGenreById(id);
    }

    /**
     * Creates a new genre.
     * @param genre The genre object to create.
     * @return The created genre object.
     */
    @Transactional
    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return genreDao.insertGenre(genre);
    }

    /**
     * Deletes a genre by ID.
     * @param id The ID of the genre to delete.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreDao.deleteGenre(id);
    }
}
