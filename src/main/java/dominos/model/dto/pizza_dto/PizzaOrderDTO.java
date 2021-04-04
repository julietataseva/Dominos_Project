package dominos.model.dto.pizza_dto;

import dominos.model.dto.DoughDTO;
import dominos.model.dto.IProductDTO;
import dominos.model.dto.ingredient_dto.IngredientWithPriceDTO;
import dominos.model.pojo.*;
import lombok.AllArgsConstructor;
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
public class PizzaOrderDTO implements IProductDTO {
    private int id;
    private PizzaAddedToCartDTO pizza;
    private DoughDTO dough;
    private PizzaSizeDTO pizzaSize;
    private List<IngredientWithPriceDTO> additionalIngredients;
    private static final int priceDifference = 3;

    public PizzaOrderDTO(int id, PizzaAddedToCartDTO pizza, DoughDTO doughDTO, PizzaSizeDTO pizzaSizeDTO,
                         List<IngredientWithPriceDTO> additionalIngredients){
        this.id = id;
        this.pizza = pizza;
        this.dough = doughDTO;
        this.pizzaSize = pizzaSizeDTO;
        this.additionalIngredients = new ArrayList<>();
        for(IngredientWithPriceDTO ingredient : additionalIngredients){
            this.additionalIngredients.add(ingredient);
        }
    }

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

        String modifiedDough = this.getModifiedDough();
        modifications.append(modifiedDough);
        if(!modifiedDough.equals("")){
            modifications.append(" ");
        }

        String modifiedSize = this.getModifiedSize();
        modifications.append(modifiedSize);
        if(!modifiedSize.equals("")){
            modifications.append(" ");
        }

        modifications.append(this.getAdditionalIngredients());

        return modifications.toString();
    }
}