package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Movie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MovieDao {
    @PersistenceContext
    private EntityManager em;

    public List<Movie> getMovies() {

        return em.createQuery("select m from Movie m", Movie.class).getResultList();
    }



    public Movie insertMovie(Movie movie) {
        if (movie.getId() == null){
            em.persist(movie);
        }else {
            em.merge(movie);
            return movie;
        }

        return movie;
    }

    public Movie getMovieById(Long id) {

        TypedQuery<Movie> query = em.createQuery("select m from Movie m where m.id = :id", Movie.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Transactional
    public void deleteMovie(Long id){
        Movie movie = em.find(Movie.class, id);
        if (movie != null) {
            em.remove(movie);
        }
    }

}
