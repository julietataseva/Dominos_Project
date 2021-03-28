package dominos.controller;

import dominos.exceptions.BadRequestException;
import dominos.model.dto.AddressRequestDTO;
import dominos.model.dto.AddressWithoutUserDTO;
import dominos.model.pojo.User;
import dominos.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AddressController extends AbstractController{
    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private AddressService addressService;

    @PutMapping("/users/{id}/addresses")
    public AddressWithoutUserDTO addAddress(@RequestBody AddressRequestDTO addressRequestDTO,
                                            HttpSession session, @PathVariable int id){
        User loggedUser = sessionManager.getLoggedUser(session);
        if (loggedUser.getId() != id) {
            throw new BadRequestException("You cannot add address to another user!");
        }

        return addressService.addAddress(addressRequestDTO, id);
    }
}