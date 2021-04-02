package dominos.model.dto;

import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.AdditionalProductImage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
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
        for(AdditionalProductImage additionalProductImage : additionalProduct.getAdditionalProductImages()){
            this.additionalProductImages.add(new AdditionalProductImageDTO(additionalProductImage));
        }
    }

    @Override
    public boolean isPizza() {
        return false;
    }

    @Override
    public String getModifications() {
        return "";
    }
}