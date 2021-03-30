package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.model.dto.AdditionalProductDTO;
import dominos.service.AdditionalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class AdditionalProductController extends AbstractController {
    @Autowired
    private AdditionalProductService additionalProductService;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/menu/products/{productId}")
    public AdditionalProductDTO getAdditionalProductById(@PathVariable int productId, HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }
        return additionalProductService.getAdditionalProductById(productId);
    }

    @GetMapping("/menu/products")
    public List<AdditionalProductDTO> getAdditionalProductsMenu(HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }
        return additionalProductService.getAdditionalProductMenu();
    }
}
