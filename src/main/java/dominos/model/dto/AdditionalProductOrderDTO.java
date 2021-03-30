package dominos.model.dto;

import dominos.model.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class AdditionalProductOrderDTO {
    private Order order;
    private AdditionalProductDTO additionalProductDTO;
    private double price;
    private int quantity;
}
