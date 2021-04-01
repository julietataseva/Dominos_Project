package dominos.model.dto;

import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.AdditionalProductImage;
import dominos.model.pojo.IProduct;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Component
public class AdditionalProductDTO implements IProduct {
    private int id;
    private String name;
    private double price;
    private List<AdditionalProductImageDTO> additionalProductImages;

    public AdditionalProductDTO(AdditionalProduct additionalProduct) {
        this.id = additionalProduct.getId();
        this.name = additionalProduct.getName();
        this.price = additionalProduct.getPrice();
        this.additionalProductImages = new ArrayList<>();
        for(AdditionalProductImage additionalProductImage : additionalProduct.getAdditionalProductImages()){
            this.additionalProductImages.add(new AdditionalProductImageDTO(additionalProductImage));
        }
    }

    @Override
    public boolean isPizza() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalProductDTO that = (AdditionalProductDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}