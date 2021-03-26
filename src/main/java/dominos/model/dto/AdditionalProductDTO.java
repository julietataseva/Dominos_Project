package dominos.model.dto;

import dominos.model.pojo.AdditionalProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class AdditionalProductDTO {
    private String name;

    public AdditionalProductDTO(AdditionalProduct additionalProduct) {
        this.name = additionalProduct.getName();
    }
}
