package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object (DAO) class for handling genre-related database operations.
 */
@Repository
public class GenreDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all genres from the database.
     *
     * @return List of all genres.
     */
    public List<Genre> getGenres() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    /**
     * Inserts a new genre into the database or updates an existing one.
     *
     * @param genre The genre object to insert or update.
     * @return The inserted or updated genre object.
     */
    public Genre insertGenre(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
        return genre;
    }

    /**
     * Retrieves a genre from the database by its ID.
     *
     * @param id The ID of the genre to retrieve.
     * @return The genre with the specified ID.
     */
    public Genre getGenreById(Long id) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.id = :id", Genre.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * Deletes a genre from the database by its ID.
     *
     * @param id The ID of the genre to delete.
     */
    @Transactional
    public void deleteGenre(Long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
        }
    }
}
