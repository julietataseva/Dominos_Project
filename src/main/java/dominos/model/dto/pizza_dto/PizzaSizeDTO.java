package dominos.model.dto.pizza_dto;

import dominos.model.pojo.PizzaSize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@Component
public class PizzaSizeDTO {
    private int id;
    private PizzaSize.Size size;

    public PizzaSizeDTO(int id, PizzaSize.Size size) {
        this.id = id;
        this.size = size;
    }

    public PizzaSizeDTO(PizzaSize pizzaSize) {
        this.id = pizzaSize.getId();
        this.size = pizzaSize.getSize();
    }
}