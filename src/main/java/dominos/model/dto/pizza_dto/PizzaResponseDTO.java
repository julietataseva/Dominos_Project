package dominos.model.dto.pizza_dto;

import dominos.model.dto.ingredient_dto.IngredientDTO;
import dominos.model.pojo.Ingredient;
import dominos.model.pojo.Pizza;
import dominos.model.pojo.PizzaImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class PizzaResponseDTO {
    private int id;
    private String name;
    private List<IngredientDTO> ingredients;
    private List<PizzaImageDTO> pizzaImages;

    public PizzaResponseDTO(Pizza pizza) {
        this.id = pizza.getId();
        this.name = pizza.getName();

        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : pizza.getIngredients()) {
            this.ingredients.add(new IngredientDTO(ingredient));
        }

        this.pizzaImages = new ArrayList<>();
        for (PizzaImage pizzaImage : pizza.getPizzaImages()) {
            this.pizzaImages.add(new PizzaImageDTO(pizzaImage));
        }
    }
}