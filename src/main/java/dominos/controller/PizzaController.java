package dominos.controller;

import dominos.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PizzaController extends AbstractController{

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/menu/pizzas")
    public

}