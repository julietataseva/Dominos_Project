package dominos.model.dto;

import dominos.model.pojo.Ingredient;
import dominos.model.pojo.Pizza;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class PizzaResponseDTO {
    private String name;
    private List<IngredientDTO> ingredients;

    public PizzaResponseDTO(Pizza pizza){
        this.name = pizza.getName();
        this.ingredients = new ArrayList<>();
        for(Ingredient ingredient : pizza.getIngredients()){
            this.ingredients.add(new IngredientDTO(ingredient));
        }
    }
}