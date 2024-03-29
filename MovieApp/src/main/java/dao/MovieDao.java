package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Movie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object (DAO) class for handling movie-related database operations.
 */
@Repository
public class MovieDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all movies from the database.
     *
     * @return List of all movies.
     */
    public List<Movie> getMovies() {
        return em.createQuery("select m from Movie m", Movie.class).getResultList();
    }

    /**
     * Retrieves a list of movies screening during the current week.
     *
     * @return List of movies screening during the current week.
     */
    public List<Movie> getWeekMovies() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        return em.createQuery("select distinct m from Movie m join m.sessions s where s.startAt between :startOfWeek and :endOfWeek", Movie.class)
                .setParameter("startOfWeek", startOfWeek.atStartOfDay())
                .setParameter("endOfWeek", endOfWeek.atTime(LocalTime.MAX))
                .getResultList();
    }

    /**
     * Inserts a new movie into the database or updates an existing one.
     *
     * @param movie The movie object to insert or update.
     * @return The inserted or updated movie object.
     */
    public Movie insertMovie(Movie movie) {
        if (movie.getId() == null) {
            em.persist(movie);
        } else {
            em.merge(movie);
        }
        return movie;
    }

    /**
     * Retrieves a movie from the database by its ID.
     *
     * @param id The ID of the movie to retrieve.
     * @return The movie with the specified ID.
     */
    public Movie getMovieById(Long id) {
        TypedQuery<Movie> query = em.createQuery("select m from Movie m where m.id = :id", Movie.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * Deletes a movie from the database by its ID.
     *
     * @param id The ID of the movie to delete.
     */
    @Transactional
    public void deleteMovie(Long id) {
        Movie movie = em.find(Movie.class, id);
        if (movie != null) {
            em.remove(movie);
        }
    }

    /**
     * Searches movies in the database based on the provided filters.
     *
     * @param filters A map of filters (field name -> filter value) to apply to the movie search.
     * @return List of movies matching the provided filters.
     */
    public List<Movie> searchMoviesByFilters(Map<String, String> filters) {
        if (filters.isEmpty()) {
            return getMovies();
        }

        StringBuilder tempQuery = new StringBuilder("SELECT m FROM Movie m WHERE");
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String filter = entry.getKey();
            tempQuery.append(String.format(" m.%s = :%s AND", filter, filter));
        }
        tempQuery.delete(tempQuery.length() - 5, tempQuery.length());

        TypedQuery<Movie> query = em.createQuery(tempQuery.toString(), Movie.class);
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String filter = entry.getKey();
            String value = entry.getValue();
            query.setParameter(filter, value);
        }

        return query.getResultList();
    }
}
