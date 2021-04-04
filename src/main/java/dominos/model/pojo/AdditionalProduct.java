package dominos.model.pojo;

import dominos.model.dto.additional_product_dto.AdditionalProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "additional_products")
public class AdditionalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    @OneToMany(mappedBy = "additionalProduct")
    private List<AdditionalProductOrder> additionalProductOrders;
    @OneToMany(mappedBy = "additionalProduct")
    private List<AdditionalProductImage> additionalProductImages;

    public AdditionalProduct(AdditionalProductDTO additionalProductDTO) {
        this.id = additionalProductDTO.getId();
        this.name = additionalProductDTO.getName();
        this.price = additionalProductDTO.getPrice();
    }
}