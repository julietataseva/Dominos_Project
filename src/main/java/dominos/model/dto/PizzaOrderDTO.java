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
    private Pizza pizza;
    private Dough dough;
    private PizzaSize pizzaSize;
    private List<Ingredient> additionalIngredients;
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

        for(Ingredient ingredient : this.additionalIngredients) {
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