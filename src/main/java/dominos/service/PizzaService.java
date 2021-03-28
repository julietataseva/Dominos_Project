package dominos.service;

import dominos.model.dto.PizzaResponseDTO;
import dominos.model.pojo.Pizza;
import dominos.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<PizzaResponseDTO> getMenu(){
        List<Pizza> pizzas = pizzaRepository.findAll();
        List<PizzaResponseDTO> returnPizzas = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            returnPizzas.add(new PizzaResponseDTO(pizza));
        }
        return returnPizzas;
    }
}