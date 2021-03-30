package dominos.model.dto;

import dominos.model.pojo.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Component
public class PizzaOrderDTO implements IProduct {
    private int id;
    private PizzaAddedToCartDTO pizza;
    private DoughDTO dough;
    private PizzaSizeDTO pizzaSize;
    private List<IngredientWithPriceDTO> additionalIngredients;
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
        double price = pizza.getPrice();

        switch (pizzaSize.getSize()){
            case MEDIUM: price -= priceDifference; break;
            case JUMBO:price += priceDifference; break;
        }

        for(IngredientWithPriceDTO ingredient : this.additionalIngredients) {
            price += ingredient.getPrice();
        }

        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaOrderDTO that = (PizzaOrderDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}