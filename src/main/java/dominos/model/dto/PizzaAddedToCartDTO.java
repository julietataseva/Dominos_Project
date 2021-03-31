package dominos.model.dto;

import dominos.model.pojo.Ingredient;
import dominos.model.pojo.Pizza;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class PizzaAddedToCartDTO {
    private int id;
    private String name;
    private List<IngredientDTO> ingredients;
    private double price;

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
