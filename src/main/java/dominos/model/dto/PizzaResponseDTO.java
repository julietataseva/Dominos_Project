package dominos.model.dto;

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

    public PizzaResponseDTO(){
        this.ingredients = new ArrayList<>();
    }
}