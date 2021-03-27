package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.model.dao.PizzaDAO;
import dominos.model.dto.PizzaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
public class PizzaController extends AbstractController{

    @Autowired
    private PizzaDAO pizzaDAO;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/menu/pizzas")
    public List<PizzaResponseDTO> getMenu(HttpSession session) throws SQLException {
        if(!sessionManager.validateLogged(session)){
            throw new AuthenticationException("You have to log in to see the menu.");
        }

        return pizzaDAO.getMenu();
    }
}