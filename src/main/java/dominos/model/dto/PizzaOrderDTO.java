package dominos.model.dto;

import dominos.model.pojo.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
@Getter
@Setter
@Component
public class PizzaOrderDTO implements IProductDTO {
    private int id;
    private PizzaAddedToCartDTO pizza;
    private DoughDTO dough;
    private PizzaSizeDTO pizzaSize;
    private List<IngredientWithPriceDTO> additionalIngredients;
    private static final int priceDifference = 3;

    public PizzaOrderDTO() {
        this.additionalIngredients = new ArrayList<>();
    }

    @Override
    public String getName() {
        return pizza.getName();
    }

    @Override
    public double getPrice() {
        double price = pizza.getPrice();

        switch (pizzaSize.getSize()) {
            case MEDIUM:
                price -= priceDifference;
                break;
            case JUMBO:
                price += priceDifference;
                break;
        }

        for (IngredientWithPriceDTO ingredient : this.additionalIngredients) {
            price += ingredient.getPrice();
        }

        return price;
    }

    @Override
    public boolean isPizza() {
        return true;
    }

    private String getModifiedDough() {
        if (dough.getType() != Dough.DoughType.TRADITIONAL) {
            return dough.getType().toString();
        }
        return "";
    }

    private String getModifiedSize() {
        if (pizzaSize.getSize() != PizzaSize.Size.LARGE) {
            return pizzaSize.getSize().toString();
        }
        return "";
    }

    private String getAdditionalIngredients() {
        if (this.additionalIngredients.isEmpty()) {
            return "";
        }

        StringBuilder additionalIngredients = new StringBuilder();
        for (IngredientWithPriceDTO ingredient : this.additionalIngredients) {
            additionalIngredients.append(ingredient.getName() + " ");
        }
        return additionalIngredients.toString();
    }

    public String getModifications() {
        StringBuilder modifications = new StringBuilder();
        modifications.append(this.getModifiedDough() + " ");
        modifications.append(this.getModifiedSize() + " ");
        modifications.append(this.getAdditionalIngredients());
        return modifications.toString();
    }
}