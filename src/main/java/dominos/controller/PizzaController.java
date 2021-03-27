package dominos.controller;

import dominos.model.dao.PizzaDAO;
import dominos.model.dto.PizzaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class PizzaController extends AbstractController{

    @Autowired

    private PizzaDAO pizzaDAO;

    @GetMapping("/menu/pizzas")
    public List<PizzaResponseDTO> getMenu() throws SQLException {
        return pizzaDAO.getMenu();
    }
}