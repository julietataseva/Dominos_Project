package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dao.DoughDAO;
import dominos.model.dao.PizzaDAO;
import dominos.model.dao.PizzaSizeDAO;
import dominos.model.dto.*;
import dominos.model.dto.pizza_dto.PizzaResponseDTO;
import dominos.model.dto.pizza_dto.PizzaResponseDetailedDTO;
import dominos.model.dto.pizza_dto.PizzaSizeDTO;
import dominos.model.dto.user_dto.ResponseUserDTO;
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

    @Autowired
    private PizzaSizeDAO pizzaSizeDAO;

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

    public PizzaResponseDetailedDTO getPizzaById(int pizzaId) {
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

    public List<DoughDTO> getMostPreferredDoughs() throws SQLException {
        return doughDAO.getMostPreferredDoughs();
    }

    public List<PizzaSizeDTO> getMostPreferredPizzaSizes() throws SQLException {
        return pizzaSizeDAO.getMostPreferredPizzaSizes();
    }

    public ResponseUserDTO getUserWithMostPizzaOrders() throws SQLException{
        return pizzaDAO.getUserWithMostPizzaOrders();
    }
}