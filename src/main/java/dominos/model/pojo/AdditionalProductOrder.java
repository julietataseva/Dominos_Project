package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
public class AdditionalProductOrder {

    private Order order;

    private AdditionalProduct additionalProduct;
    private double price;
    private int quantity;
}
