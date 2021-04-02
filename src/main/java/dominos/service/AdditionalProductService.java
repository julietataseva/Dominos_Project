package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dao.AdditionalProductDAO;
import dominos.model.dto.AdditionalProductDTODTO;
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

    public AdditionalProductDTODTO getAdditionalProductById(int productID) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(productID);

        if (additionalProduct.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        return new AdditionalProductDTODTO(additionalProduct.get());
    }

    public List<AdditionalProductDTODTO> getAdditionalProductMenu() {
        List<AdditionalProduct> products = additionalProductRepository.findAll();
        List<AdditionalProductDTODTO> returnProducts = new ArrayList<>();
        for (AdditionalProduct product : products) {
            returnProducts.add(new AdditionalProductDTODTO(product));
        }
        return returnProducts;
    }

    public List<AdditionalProductDTODTO> getMostSoldAdditionalProducts() throws SQLException {
        return additionalProductDAO.getMostSoldAdditionalProducts();
    }
}