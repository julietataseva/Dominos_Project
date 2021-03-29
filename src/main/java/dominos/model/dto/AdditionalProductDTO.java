package dominos.model.dto;

import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.IProduct;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Component
@EqualsAndHashCode
public class AdditionalProductDTO implements IProduct {
    private String name;

    public AdditionalProductDTO(AdditionalProduct additionalProduct) {
        this.name = additionalProduct.getName();
    }
}