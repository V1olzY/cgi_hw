package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SessionDao {
    @PersistenceContext
    private EntityManager em;

    public List<Session> getSessions() {
        return em.createQuery("select s from Session s", Session.class).getResultList();
    }

    public Session insertSession(Session session) {
        if (session.getId() == null) {
            em.persist(session);
        } else {
            em.merge(session);
            return session;
        }
        return session;
    }

    public Session getSessionById(Long id) {
        TypedQuery<Session> query = em.createQuery("select s from Session s where s.id = :id", Session.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public void deleteSession(Long id) {
        Session session = em.find(Session.class, id);
        if (session != null) {
            em.remove(session);
        }
    }
}
