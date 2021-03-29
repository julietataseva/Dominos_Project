package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.pojo.IProduct;
import dominos.model.pojo.User;
import dominos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Component
public class SessionManager {

    private static final String LOGGED_USER_ID = "LOGGED_USER_ID";
    private static final String USER_CART = "USER_CART";

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

    public void loginUser(HttpSession session, int id) {
        session.setAttribute(LOGGED_USER_ID, id);
        session.setAttribute(USER_CART, new ArrayList<IProduct>());
    }

    public void logoutUser(HttpSession session) {
        session.invalidate();
    }

    public boolean validateLogged(HttpSession session) {
        return !session.isNew() && session.getAttribute(LOGGED_USER_ID) != null;
    }

    public ArrayList<IProduct> getCartAttribute(HttpSession session) {
        return (ArrayList<IProduct>) session.getAttribute("USER_CART");
    }
}