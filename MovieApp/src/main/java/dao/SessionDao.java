package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Seat;
import model.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) class for handling session-related database operations.
 */
@Repository
public class SessionDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all sessions from the database.
     *
     * @return List of all sessions.
     */
    public List<Session> getSessions() {
        return em.createQuery("select s from Session s", Session.class).getResultList();
    }

    /**
     * Inserts a new session into the database or updates an existing one.
     *
     * @param session The session object to insert or update.
     * @return The inserted or updated session object.
     */
    public Session insertSession(Session session) {
        if (session.getId() == null) {
            em.persist(session);
        } else {
            em.merge(session);
        }
        return session;
    }

    /**
     * Retrieves a session from the database by its ID.
     *
     * @param id The ID of the session to retrieve.
     * @return The session with the specified ID.
     */
    public Session getSessionById(Long id) {
        TypedQuery<Session> query = em.createQuery("select s from Session s where s.id = :id", Session.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * Deletes a session from the database by its ID.
     *
     * @param id The ID of the session to delete.
     */
    @Transactional
    public void deleteSession(Long id) {
        Session session = em.find(Session.class, id);
        if (session != null) {
            em.remove(session);
        }
    }

    /**
     * Retrieves a list of all seats in the cinema hall for a given session.
     * The cinema hall has 9 rows and 10 seats in every row.
     *
     * @param sessionId The ID of the session.
     * @return List of all seats in the cinema hall.
     */
    public List<Seat> getAllSeats(Long sessionId) {
        List<Seat> allSeats = new ArrayList<>();
        Random random = new Random();

        for (int row = 0; row < 9; row++) {
            for (int seatInRow = 0; seatInRow < 10; seatInRow++) {
                allSeats.add(new Seat(row + 1, seatInRow + 1, random.nextBoolean()));
            }
        }
        return allSeats;
    }

    /**
     * Retrieves a list of available seats for a given session.
     *
     * @param sessionId The ID of the session.
     * @return List of available seats for the session.
     */
    public List<Seat> getAvailableSeats(Long sessionId) {
        return getAllSeats(sessionId).stream()
                .filter(Seat::isAvailable)
                .collect(Collectors.toList());
    }
}
