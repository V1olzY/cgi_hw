package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Language;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LanguageDao {
    @PersistenceContext
    private EntityManager em;

    public List<Language> getLanguages() {
        return em.createQuery("select l from Language l", Language.class).getResultList();
    }

    public Language insertLanguage(Language language) {
        if (language.getId() == null) {
            em.persist(language);
        } else {
            em.merge(language);
            return language;
        }
        return language;
    }

    public Language getLanguageById(Long id) {
        TypedQuery<Language> query = em.createQuery("select l from Language l where l.id = :id", Language.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public void deleteLanguage(Long id) {
        Language language = em.find(Language.class, id);
        if (language != null) {
            em.remove(language);
        }
    }
}
