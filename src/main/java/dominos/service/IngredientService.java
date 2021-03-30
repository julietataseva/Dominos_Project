package dominos.service;

import dominos.model.dto.IngredientDTO;
import dominos.model.pojo.Ingredient;
import dominos.model.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientDTO> ingredientDTOS = new ArrayList<>();
        for (Ingredient i : ingredients) {
            ingredientDTOS.add(new IngredientDTO(i));
        }
        return ingredientDTOS;
    }
}
