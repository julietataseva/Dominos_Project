package dominos.model.dto;

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

    public CartResponseDTO(IProductDTO product, int quantity) {
        this.name = product.getName();
        this.quantity = quantity;
        this.price = product.getPrice() * quantity;
    }
}