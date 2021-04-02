package dominos.controller;

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
        sessionManager.validateLogged(session);
        User loggedUser = sessionManager.getLoggedUser(session);
        return addressService.addAddress(addressRequestDTO, loggedUser);
    }

    @DeleteMapping("/addresses/{addressId}")
    public AddressWithoutUserDTO deleteAddress(@PathVariable int addressId, HttpSession session) {
        sessionManager.validateLogged(session);
        User loggedUser = sessionManager.getLoggedUser(session);
        return addressService.deleteAddress(addressId, loggedUser);
    }

    @GetMapping("/addresses")
    public List<AddressWithoutUserDTO> getAllAddressesOfUser(HttpSession session) {
        sessionManager.validateLogged(session);
        User loggedUser = sessionManager.getLoggedUser(session);
        return addressService.getAllAddressesOfUser(loggedUser);
    }

    @PostMapping("/addresses/{addressId}")
    public AddressWithoutUserDTO editAddress(@RequestBody AddressRequestDTO addressRequestDTO, HttpSession session,
                                             @PathVariable int addressId) {

        sessionManager.validateLogged(session);
        User loggedUser = sessionManager.getLoggedUser(session);
        return addressService.editAddress(addressRequestDTO, loggedUser, addressId);
    }

    @PutMapping("/addresses/{addressId}")
    public AddressWithoutUserDTO chooseAddressForCurrentOrder(@PathVariable int addressId, HttpSession session) {
        sessionManager.validateLogged(session);
        User loggedUser = sessionManager.getLoggedUser(session);

        AddressWithoutUserDTO addressWithoutUserDTO = addressService.chooseAddressForCurrentOrder(addressId, loggedUser);
        sessionManager.setAddressIdAttribute(session, addressId);
        return addressWithoutUserDTO;
    }
}