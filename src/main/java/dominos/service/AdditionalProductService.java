package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dao.AdditionalProductDAO;
import dominos.model.dto.AdditionalProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.repository.AdditionalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdditionalProductService {
    @Autowired
    private AdditionalProductRepository additionalProductRepository;

    @Autowired
    private AdditionalProductDAO additionalProductDAO;

    public AdditionalProductDTO getAdditionalProductById(int productID) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(productID);

        if (additionalProduct.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        return new AdditionalProductDTO(additionalProduct.get());
    }

    public List<AdditionalProductDTO> getAdditionalProductMenu() {
        List<AdditionalProduct> products = additionalProductRepository.findAll();
        List<AdditionalProductDTO> returnProducts = new ArrayList<>();
        for (AdditionalProduct product : products) {
            returnProducts.add(new AdditionalProductDTO(product));
        }
        return returnProducts;
    }

    public List<AdditionalProductDTO> getMostSoldAdditionalProducts() throws SQLException {
        return additionalProductDAO.getMostSoldAdditionalProducts();
    }
}
