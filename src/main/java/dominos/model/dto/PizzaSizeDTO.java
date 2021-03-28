package dominos.model.dto;

import dominos.model.pojo.PizzaSize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PizzaSizeDTO {
    private PizzaSize.Size size;

    public PizzaSizeDTO(PizzaSize.Size size){
        this.size = size;
    }
}