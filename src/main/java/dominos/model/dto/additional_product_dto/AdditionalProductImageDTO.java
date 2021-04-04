package dominos.model.dto.additional_product_dto;


import dominos.model.pojo.AdditionalProductImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AdditionalProductImageDTO {
    private int id;

    public AdditionalProductImageDTO(AdditionalProductImage additionalProductImage) {
        this.id = additionalProductImage.getId();
    }
}