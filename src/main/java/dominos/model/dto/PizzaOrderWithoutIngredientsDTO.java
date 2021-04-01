package dominos.model.dto;

import dominos.model.pojo.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PizzaOrderWithoutIngredientsDTO {
    private Order order;
    private PizzaAddedToCartDTO pizza;
    private DoughDTO dough;
    private PizzaSizeDTO pizzaSize;
}