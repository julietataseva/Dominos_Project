package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.model.pojo.User;
import dominos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    private static final String LOGGED_USER_ID = "LOGGED_USER_ID";

    @Autowired
    private UserRepository repository;

    public User getLoggedUser(HttpSession session) {
        if (!validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        } else {
            int userId = (int) session.getAttribute(LOGGED_USER_ID);
            return repository.findById(userId).get();
        }
    }

    public void loginUser(HttpSession ses, int id) {
        ses.setAttribute(LOGGED_USER_ID, id);
    }

    public void logoutUser(HttpSession ses) {
        ses.invalidate();
    }

    public static boolean validateLogged(HttpSession session) {
        if (session.isNew() || session.getAttribute(LOGGED_USER_ID) == null) {
            return false;
        } else {
            return true;
        }
    }
}