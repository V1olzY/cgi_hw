package controller;

import dao.LanguageDao;
import model.Language;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageDao languageDao;

    /**
     * Constructor for LanguageController.
     * @param languageDao Instance of LanguageDao.
     */
    public LanguageController(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }

    /**
     * Retrieves all languages.
     * @return List of all languages.
     */
    @GetMapping
    public List<Language> getAllLanguages() {
        return languageDao.getLanguages();
    }

    /**
     * Retrieves a language by ID.
     * @param id The ID of the language to retrieve.
     * @return The language with the specified ID.
     */
    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable Long id) {
        return languageDao.getLanguageById(id);
    }

    /**
     * Creates a new language.
     * @param language The language object to create.
     * @return The created language object.
     */
    @Transactional
    @PostMapping
    public Language createLanguage(@RequestBody Language language) {
        return languageDao.insertLanguage(language);
    }

    /**
     * Deletes a language by ID.
     * @param id The ID of the language to delete.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteLanguage(@PathVariable Long id) {
        languageDao.deleteLanguage(id);
    }
}
