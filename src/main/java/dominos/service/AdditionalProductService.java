package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.AdditionalProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.repository.AdditionalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdditionalProductService {
    @Autowired
    private AdditionalProductRepository additionalProductRepository;

    public AdditionalProductDTO getAdditionalProductByName(String name) {
        AdditionalProduct additionalProduct = additionalProductRepository.findByName(name);

        if (additionalProduct == null) {
            throw new NotFoundException("Product not found");
        }

        return new AdditionalProductDTO(additionalProduct);
    }
}
