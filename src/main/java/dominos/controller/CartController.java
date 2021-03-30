package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.NotFoundException;
import dominos.model.dto.AdditionalProductDTO;
import dominos.model.dto.CartResponseDTO;
import dominos.model.dto.PizzaOrderDTO;
import dominos.model.dto.RequestPizzaOrderDTO;
import dominos.model.pojo.IProduct;
import dominos.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        Map<IProduct, Integer> cart = sessionManager.getCartAttribute(session);

        return cartService.addAdditionalProductToCart(productId, cart);
    }

    @DeleteMapping("/cart/products/{productId}")
    public AdditionalProductDTO deleteAdditionalProductFromCart(@PathVariable int productId, HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        Map<IProduct, Integer> cart = sessionManager.getCartAttribute(session);

        return cartService.deleteAdditionalProductFromCart(productId, cart);
    }

    @PutMapping("/menu/pizzas/{pizzaId}")
    public PizzaOrderDTO addPizzaToCart(@PathVariable int pizzaId, HttpSession session,
                                                 @RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in in order to add pizza to cart!");
        }

        Map<IProduct, Integer> cart = sessionManager.getCartAttribute(session);
        return cartService.addPizzaToCart(pizzaId, requestPizzaOrderDTO, cart);
    }

    @DeleteMapping("/cart/pizzas/{pizzaId}")
    public ResponseEntity<String> removePizzaFromCart(@PathVariable int pizzaId, HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            return new ResponseEntity<>("You have to log in in order to remove pizza from cart!",
                    HttpStatus.UNAUTHORIZED);
        }

        Map<IProduct, Integer> cart = sessionManager.getCartAttribute(session);

        try {
            String response = cartService.removePizzaFromCart(pizzaId, cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart")
    public List<CartResponseDTO> getCart(HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        if (sessionManager.getCartAttribute(session) == null) {
            throw new NotFoundException("Cart is empty!");
        }

        Map<IProduct, Integer> cartAttribute = sessionManager.getCartAttribute(session);
        return cartService.getCart(cartAttribute);
    }
}