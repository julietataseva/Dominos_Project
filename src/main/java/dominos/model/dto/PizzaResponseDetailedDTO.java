package dominos.model.dto;

import dominos.model.pojo.Dough;
import dominos.model.pojo.Ingredient;
import dominos.model.pojo.Pizza;
import dominos.model.pojo.PizzaSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class PizzaResponseDetailedDTO {
    private int id;
    private String name;
    private List<IngredientDTO> ingredients;
    private double price;
    private DoughDTO dough;
    private PizzaSizeDTO pizzaSize;

    public PizzaResponseDetailedDTO(Pizza pizza) {
        this.id = pizza.getId();
        this.name = pizza.getName();
        this.price = pizza.getPrice();
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : pizza.getIngredients()) {
            this.ingredients.add(new IngredientDTO(ingredient));
        }

        this.dough = new DoughDTO(Dough.DoughType.TRADITIONAL);
        this.pizzaSize = new PizzaSizeDTO(PizzaSize.Size.LARGE);
    }
}