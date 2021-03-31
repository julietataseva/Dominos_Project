package dominos.controller;

import dominos.model.dto.PizzaResponseDTO;
import dominos.model.dto.PizzaResponseDetailedDTO;
import dominos.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PizzaController extends AbstractController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/menu/pizzas")
    public List<PizzaResponseDTO> getMenu(HttpSession session) {
        sessionManager.validateLogged(session);
        return pizzaService.getMenu();
    }

    @GetMapping("menu/pizzas/{pizzaId}")
    public PizzaResponseDetailedDTO getPizzaById(@PathVariable int pizzaId, HttpSession session) {
        sessionManager.validateLogged(session);
        return pizzaService.getById(pizzaId);
    }
}
