package dominos.controller;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.additional_product_dto.AdditionalProductDTO;
import dominos.model.dto.CartResponseDTO;
import dominos.model.dto.pizza_dto.PizzaOrderDTO;
import dominos.model.dto.pizza_dto.RequestPizzaOrderDTO;
import dominos.model.dto.IProductDTO;
import dominos.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class CartController extends AbstractController {
    @Autowired
    private CartService cartService;

    @Autowired
    private SessionManager sessionManager;

    @PutMapping("/menu/products/{productId}")
    public AdditionalProductDTO addAdditionalProductToCart(@PathVariable int productId, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.addAdditionalProductToCart(productId, cart);
    }

    @PutMapping("/cart/products/decrease/{productId}")
    public AdditionalProductDTO decreaseAdditionalProductQuantityInCart(@PathVariable int productId, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.decreaseAdditionalProductQuantityInCart(productId, cart);
    }

    @DeleteMapping("/cart/products/delete/{productId}")
    public AdditionalProductDTO deleteAdditionalProductFromCart(@PathVariable int productId, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.deleteAdditionalProductFromCart(productId, cart);
    }

    @PutMapping("/menu/pizzas")
    public PizzaOrderDTO addPizzaToCart(HttpSession session,
                                        @RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.addPizzaToCart(requestPizzaOrderDTO, cart);
    }

    @PutMapping("/cart/pizzas/decrease")
    public PizzaOrderDTO decreasePizzaQuantityInCart(@RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.decreasePizzaQuantityInCart(requestPizzaOrderDTO, cart);
    }

    @DeleteMapping("/cart/pizzas/delete")
    public PizzaOrderDTO deletePizzaFromCart(@RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.deletePizzaFromCart(requestPizzaOrderDTO, cart);
    }

    @GetMapping("/cart")
    public List<CartResponseDTO> getCart(HttpSession session) {
        sessionManager.validateLogged(session);

        Map<IProductDTO, Integer> cartAttribute = sessionManager.getCartAttribute(session);
        return cartService.getCart(cartAttribute);
    }
}