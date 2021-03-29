package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.AdditionalProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.IProduct;
import dominos.model.repository.AdditionalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private AdditionalProductRepository additionalProductRepository;


    public String addAdditionalProductToCart(int id, ArrayList<IProduct> cart) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(id);
        if (additionalProduct.isEmpty()) {
            throw new NotFoundException("No such product");
        }

        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());

        cart.add(additionalProductDTO);
        return "Product added successfully";
    }
}
