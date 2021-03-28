package dominos.model.dto;

import dominos.model.pojo.PizzaSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PizzaSizeDTO {
    private PizzaSize.Size size;
}