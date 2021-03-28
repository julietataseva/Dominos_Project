package dominos.model.dto;

import dominos.model.pojo.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class IngredientDTO {
    private String name;

    public IngredientDTO(Ingredient i) {
        this.name = i.getName();
    }
}