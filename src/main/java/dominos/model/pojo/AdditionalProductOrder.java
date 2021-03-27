package dominos.model.pojo;

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
    private int id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private AdditionalProduct additionalProduct;
    private double price;
    private int quantity;
}
