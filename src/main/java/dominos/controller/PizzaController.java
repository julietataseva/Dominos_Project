package dominos.controller;

import dominos.model.dto.*;
import dominos.model.pojo.User;
import dominos.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
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

    @GetMapping("/menu/pizzas/top")
    public List<PizzaResponseDTO> getMostSoldPizzas(HttpSession session) throws SQLException {
        sessionManager.validateLogged(session);
        return pizzaService.getMostSoldPizzas();
    }

    @GetMapping("/menu/pizzas/dough")
    public DoughDTO getMostPreferredDough(HttpSession session){
        sessionManager.validateLogged(session);
        return pizzaService.getMostPreferredDough();
    }

    @GetMapping("/menu/pizzas/size")
    public PizzaSizeDTO getMostPreferredPizzaSize(HttpSession session){
        sessionManager.validateLogged(session);
        return pizzaService.getMostPreferredPizzaSize();
    }

    @GetMapping("/menu/pizzas/top-fan")
    public LoginResponseUserDTO getUserWithMostPizzaOrders(HttpSession session){
        sessionManager.validateLogged(session);
        return pizzaService.getUserWithMostPizzaOrders();
    }
}