package controller;

import dao.GenreDao;
import model.Genre;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private GenreDao genreDao;

    public GenreController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreDao.getGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        return genreDao.getGenreById(id);
    }

    @Transactional
    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return genreDao.insertGenre(genre);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreDao.deleteGenre(id);
    }
}
