package controller;


import dao.LanguageDao;
import model.Language;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    private LanguageDao languageDao;


    public LanguageController(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }

    @GetMapping
    public List<Language> getAllLanguages() {
        return languageDao.getLanguages();
    }

    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable Long id) {
        return languageDao.getLanguageById(id);
    }

    @Transactional
    @PostMapping
    public Language createLanguage(@RequestBody Language language) {
        return languageDao.insertLanguage(language);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteLanguage(@PathVariable Long id) {
        languageDao.deleteLanguage(id);
    }
}

