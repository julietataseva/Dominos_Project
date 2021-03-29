package dominos.controller;
import dominos.exceptions.BadRequestException;
import dominos.exceptions.AuthenticationException;
import dominos.exceptions.NotFoundException;
import dominos.model.pojo.IProduct;
import dominos.model.pojo.User;
import dominos.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private SessionManager sessionManager;


    @PutMapping("/menu/products/{id}")
    public ResponseEntity<String> addAdditionalProductToCart(@PathVariable int id, HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            return new ResponseEntity<>("Invalid session, you have to log in", HttpStatus.UNAUTHORIZED);
        }

        Map<IProduct, Integer> cart = sessionManager.getCartAttribute(session);

        try {
            String response = cartService.addAdditionalProductToCart(id, cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart")
    public Map<IProduct, Integer> getCart(HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        if (sessionManager.getCartAttribute(session) == null) {
            throw new NotFoundException("Cart is empty!");
        }

        return sessionManager.getCartAttribute(session);
    }
}