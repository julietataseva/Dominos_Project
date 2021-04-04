package dominos.model.dto.pizza_dto;

import dominos.model.pojo.PizzaImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PizzaImageDTO {
    private int id;

    public PizzaImageDTO(PizzaImage pizzaImage) {
        this.id = pizzaImage.getId();
    }
}