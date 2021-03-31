package dominos.model.dto;

import dominos.model.pojo.*;
import lombok.Getter;
import lombok.Setter;
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
    private List<PizzaImageDTO> pizzaImages;

    public PizzaResponseDetailedDTO(Pizza pizza) {
        this.id = pizza.getId();
        this.name = pizza.getName();
        this.price = pizza.getPrice();
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : pizza.getIngredients()) {
            this.ingredients.add(new IngredientDTO(ingredient));
        }

        this.dough = new DoughDTO(1, Dough.DoughType.TRADITIONAL);
        this.pizzaSize = new PizzaSizeDTO(2, PizzaSize.Size.LARGE);
        this.pizzaImages = new ArrayList<>();
        for(PizzaImage pizzaImage : pizza.getPizzaImages()){
            this.pizzaImages.add(new PizzaImageDTO(pizzaImage));
        }
    }
}