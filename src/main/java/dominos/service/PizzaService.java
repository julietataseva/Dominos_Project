package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dao.DoughDAO;
import dominos.model.dao.PizzaDAO;
import dominos.model.dto.DoughDTO;
import dominos.model.dto.PizzaResponseDTO;
import dominos.model.dto.PizzaResponseDetailedDTO;
import dominos.model.pojo.Pizza;
import dominos.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaDAO pizzaDAO;

    @Autowired
    private DoughDAO doughDAO;

    public List<PizzaResponseDTO> getMenu() {
        List<Pizza> pizzas = pizzaRepository.findAll();
        if (pizzas == null || pizzas.isEmpty()) {
            throw new NotFoundException("The menu is empty.");
        }

        List<PizzaResponseDTO> returnPizzas = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            returnPizzas.add(new PizzaResponseDTO(pizza));
        }
        return returnPizzas;
    }

    public PizzaResponseDetailedDTO getById(int pizzaId) {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(pizzaId);
        if (optionalPizza.isEmpty()) {
            throw new BadRequestException("This pizza doesn't exist.");
        }

        Pizza pizza = optionalPizza.get();
        return new PizzaResponseDetailedDTO(pizza);
    }

    public List<PizzaResponseDTO> getMostSoldPizzas() throws SQLException {
        return pizzaDAO.getMostSoldPizzas();
    }

    public DoughDTO getMostPreferredDough() {
        return doughDAO.getMostPreferredDough();
    }
}