package dominos.model.dto.ingredient_dto;

import dominos.model.pojo.Ingredient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Component
public class IngredientWithPriceDTO {
    private int id;
    private String name;
    private double price;

    public IngredientWithPriceDTO(Ingredient i) {
        this.id = i.getId();
        this.name = i.getName();
        this.price = i.getPrice();
    }
}