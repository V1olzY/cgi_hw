package controller;

import dao.SessionDao;
import model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {


    private SessionDao sessionDao;

    SessionController(SessionDao sessionDao){this.sessionDao = sessionDao;}

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionDao.getSessions();
    }

    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return sessionDao.getSessionById(id);
    }

    @Transactional
    @PostMapping
    public Session createSession(@RequestBody Session session) {
        return sessionDao.insertSession(session);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionDao.deleteSession(id);
    }
}
