package dominos.model.pojo;

import dominos.model.dto.AdditionalProductDTO;
import dominos.model.dto.AdditionalProductImageDTO;
import dominos.model.repository.AdditionalProductImageRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        this.additionalProductImages = new ArrayList<>();
    }
}