package dominos.model.dto;

import dominos.model.pojo.Dough;
import dominos.model.pojo.IProduct;
import dominos.model.pojo.Pizza;
import dominos.model.pojo.PizzaSize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class PizzaOrderDTO implements IProduct {
    private Pizza pizza;
    private Dough dough;
    private PizzaSize pizzaSize;
    private List<IngredientDTO> additionalIngredients;
    private static final int priceDifference = 3;
    public PizzaOrderDTO(){
        this.additionalIngredients = new ArrayList<>();
    }

    @Override
    public String getName() {
        return pizza.getName();
    }

    @Override
    public double getPrice() {
        return pizza.getPrice();
    }
}