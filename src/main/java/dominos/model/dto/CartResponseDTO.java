package dominos.model.dto;

import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.IProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@Component
public class CartResponseDTO {
    private String name;
    private int quantity;
    private double price;

    public CartResponseDTO(IProduct product, int quantity) {
        this.name = product.getName();
        this.quantity = quantity;
        this.price = product.getPrice()*quantity;
    }
}