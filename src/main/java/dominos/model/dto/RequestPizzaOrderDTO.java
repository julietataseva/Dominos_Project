package dominos.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Component
public class RequestPizzaOrderDTO {
    private Integer doughTypeId;
    private Integer pizzaSizeId;
    private List<Integer> additionalIngredientsIds;
}
