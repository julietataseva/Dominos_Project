package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.model.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController extends AbstractController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/users/{id}/orders")
    public Map<Integer, Map<LocalDate, List<String>>> getAllMadeOrdersByUserId(@PathVariable int id, HttpSession session) throws SQLException {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        if (sessionManager.getLoggedUser(session).getId() != id) {
            throw new AuthenticationException("You have to be logged in your account");
        }

        Map<Integer, Map<LocalDate, List<String>>> orders = orderDAO.getAllMadeOrdersByUserId(id);
        return orders;
    }
}
