package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.AddressRequestDTO;
import dominos.model.dto.AddressWithoutUserDTO;
import dominos.model.pojo.User;
import dominos.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AddressController extends AbstractController {
    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private AddressService addressService;


    @PutMapping("/addresses")
    public AddressWithoutUserDTO addAddress(@RequestBody AddressRequestDTO addressRequestDTO, HttpSession session) {
        if(!sessionManager.validateLogged(session)){
            throw new AuthenticationException("You have to log in in order to add address!");
        }

        User loggedUser = sessionManager.getLoggedUser(session);
        return addressService.addAddress(addressRequestDTO, loggedUser);
    }

    @DeleteMapping("/addresses/{addressId}")
    public AddressWithoutUserDTO deleteAddress(@PathVariable int addressId, HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        User loggedUser = sessionManager.getLoggedUser(session);

        return addressService.deleteAddress(addressId, loggedUser);
    }

    @GetMapping("/addresses")
    public List<AddressWithoutUserDTO> getAllAddressesOfUser(HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in in order to see your addresses!");
        }

        User loggedUser = sessionManager.getLoggedUser(session);

        return addressService.getAllAddressesOfUser(loggedUser);
    }

    @PostMapping("/addresses/{addressId}")
    public AddressWithoutUserDTO editAddress(@RequestBody AddressRequestDTO addressRequestDTO, HttpSession session,
                                             @PathVariable int addressId) {

        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in in order to edit address!");
        }

        User loggedUser = sessionManager.getLoggedUser(session);

        return addressService.editAddress(addressRequestDTO, loggedUser, addressId);
    }
}