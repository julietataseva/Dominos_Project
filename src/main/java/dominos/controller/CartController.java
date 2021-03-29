package dominos.controller;

import dominos.exceptions.BadRequestException;
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
import java.util.ArrayList;

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

        ArrayList<IProduct> cart = sessionManager.getCartAttribute(session);

        try {
            String response = cartService.addAdditionalProductToCart(id, cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart")
    public ArrayList<IProduct> getCart(HttpSession session, @PathVariable int id){
        return cartService.getCart(session);
    }
}