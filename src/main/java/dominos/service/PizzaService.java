package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.exceptions.NoContentException;
import dominos.model.dto.PizzaResponseDTO;
import dominos.model.dto.PizzaResponseDetailedDTO;
import dominos.model.pojo.Pizza;
import dominos.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<PizzaResponseDTO> getMenu(){
        List<Pizza> pizzas = pizzaRepository.findAll();
        if(pizzas == null || pizzas.isEmpty()){
            throw new NoContentException("The menu is empty.");
        }

        List<PizzaResponseDTO> returnPizzas = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            returnPizzas.add(new PizzaResponseDTO(pizza));
        }
        return returnPizzas;
    }

    public PizzaResponseDetailedDTO getById(int pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId).get();
        if(pizza == null){
            throw new BadRequestException("Pizza with this name doesn't exist.");
        }

        return new PizzaResponseDetailedDTO(pizza);
    }
}