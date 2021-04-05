package dominos.model.dto.additional_product_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dominos.model.dto.IProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.AdditionalProductImage;
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
public class AdditionalProductDTO implements IProductDTO {
    private int id;
    private String name;
    private double price;
    private List<AdditionalProductImageDTO> additionalProductImages;

    public AdditionalProductDTO(AdditionalProduct additionalProduct) {
        this.id = additionalProduct.getId();
        this.name = additionalProduct.getName();
        this.price = additionalProduct.getPrice();
        this.additionalProductImages = new ArrayList<>();
        for (AdditionalProductImage additionalProductImage : additionalProduct.getAdditionalProductImages()) {
            this.additionalProductImages.add(new AdditionalProductImageDTO(additionalProductImage));
        }
    }

    @Override
    @JsonIgnore
    public boolean isPizza() {
        return false;
    }

    @Override
    @JsonIgnore
    public String getModifications() {
        return "";
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