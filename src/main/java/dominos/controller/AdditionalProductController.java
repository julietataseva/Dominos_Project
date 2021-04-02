package dominos.controller;

import dominos.model.dto.additional_product_dto.AdditionalProductDTO;
import dominos.service.AdditionalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;


@RestController
public class AdditionalProductController extends AbstractController {
    @Autowired
    private AdditionalProductService additionalProductService;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/menu/products/{productId}")
    public AdditionalProductDTO getAdditionalProductById(@PathVariable int productId, HttpSession session) {
        sessionManager.validateLogged(session);
        return additionalProductService.getAdditionalProductById(productId);
    }

    @GetMapping("/menu/products")
    public List<AdditionalProductDTO> getAdditionalProductsMenu(HttpSession session) {
        sessionManager.validateLogged(session);
        return additionalProductService.getAdditionalProductMenu();
    }

    @GetMapping("/menu/products/top")
    public List<AdditionalProductDTO> getMostSoldAdditionalProducts(HttpSession session) throws SQLException {
        sessionManager.validateLogged(session);
        return additionalProductService.getMostSoldAdditionalProducts();
    }
}