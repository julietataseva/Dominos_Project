package dominos.model.dto;

import dominos.model.pojo.Dough;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class PizzaResponseDetailedDTO {
    private String name;
    private List<IngredientDTO> ingredients;
    private double price;
    private DoughDTO dough;
    private PizzaSizeDTO size;

    public PizzaResponseDetailedDTO(){
        this.ingredients = new ArrayList<>();
    }
}