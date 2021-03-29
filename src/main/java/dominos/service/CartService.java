package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.AdditionalProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.IProduct;
import dominos.model.repository.AdditionalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private AdditionalProductRepository additionalProductRepository;


    public String addAdditionalProductToCart(int productID, Map<IProduct, Integer> cart) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(productID);
        if (additionalProduct.isEmpty()) {
            throw new NotFoundException("No such product");
        }

        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());

        if (!cart.containsKey(additionalProductDTO)) {
            cart.put(additionalProductDTO, 1);
        } else {
            cart.put(additionalProductDTO, cart.get(additionalProductDTO) + 1);
        }

        return "Product added successfully";
    }
}
