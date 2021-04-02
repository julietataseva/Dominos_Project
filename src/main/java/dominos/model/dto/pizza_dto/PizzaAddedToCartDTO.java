package dominos.model.dto.pizza_dto;

import dominos.model.dto.ingredient_dto.IngredientDTO;
import dominos.model.pojo.Ingredient;
import dominos.model.pojo.Pizza;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Component
public class PizzaAddedToCartDTO {
    private int id;
    private String name;
    private double price;
    private List<IngredientDTO> ingredients;

    public PizzaAddedToCartDTO(Pizza pizza) {
        this.id = pizza.getId();
        this.name = pizza.getName();
        this.price = pizza.getPrice();
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : pizza.getIngredients()) {
            this.ingredients.add(new IngredientDTO(ingredient));
        }
    }
}