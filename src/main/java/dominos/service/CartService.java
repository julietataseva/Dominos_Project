package dominos.service;

import dominos.controller.SessionManager;
import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dto.AdditionalProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.IProduct;
import dominos.model.pojo.User;
import dominos.model.repository.AdditionalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private AdditionalProductRepository additionalProductRepository;

    @Autowired
    private SessionManager sessionManager;


    public String addAdditionalProductToCart(int id, ArrayList<IProduct> cart) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(id);
        if (additionalProduct.isEmpty()) {
            throw new NotFoundException("No such product");
        }

        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());

        cart.add(additionalProductDTO);
        return "Product added successfully";
    }

    public ArrayList<IProduct> getCart(HttpSession session) {
        User loggedUser = sessionManager.getLoggedUser(session);
        /*
        if (loggedUser.getId() != id) {
            throw new BadRequestException("You cannot edit the profile of another user!");
        }


         */
        if(sessionManager.getCartAttribute(session) == null){
            throw new NotFoundException("Cart is empty!");
        }

        return sessionManager.getCartAttribute(session);
    }
}