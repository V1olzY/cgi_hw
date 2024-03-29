package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.Language;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object (DAO) class for handling language-related database operations.
 */
@Repository
public class LanguageDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all languages from the database.
     *
     * @return List of all languages.
     */
    public List<Language> getLanguages() {
        return em.createQuery("select l from Language l", Language.class).getResultList();
    }

    /**
     * Inserts a new language into the database or updates an existing one.
     *
     * @param language The language object to insert or update.
     * @return The inserted or updated language object.
     */
    public Language insertLanguage(Language language) {
        if (language.getId() == null) {
            em.persist(language);
        } else {
            em.merge(language);
        }
        return language;
    }

    /**
     * Retrieves a language from the database by its ID.
     *
     * @param id The ID of the language to retrieve.
     * @return The language with the specified ID.
     */
    public Language getLanguageById(Long id) {
        TypedQuery<Language> query = em.createQuery("select l from Language l where l.id = :id", Language.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * Deletes a language from the database by its ID.
     *
     * @param id The ID of the language to delete.
     */
    @Transactional
    public void deleteLanguage(Long id) {
        Language language = em.find(Language.class, id);
        if (language != null) {
            em.remove(language);
        }
    }
}
