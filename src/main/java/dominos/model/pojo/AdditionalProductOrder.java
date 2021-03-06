package dominos.model.pojo;

import dominos.model.dto.additional_product_dto.AdditionalProductOrderDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders_have_additional_products")
public class AdditionalProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "additional_product_id")
    private AdditionalProduct additionalProduct;
    private double price;
    private int quantity;

    public AdditionalProductOrder(AdditionalProductOrderDTO additionalProductOrderDTO) {
        this.order = additionalProductOrderDTO.getOrder();
        this.additionalProduct =
                new AdditionalProduct(additionalProductOrderDTO.getAdditionalProductDTO());
        this.price = additionalProductOrderDTO.getPrice();
        this.quantity = additionalProductOrderDTO.getQuantity();
    }
}