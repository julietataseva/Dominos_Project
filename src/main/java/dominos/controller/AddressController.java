package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.AddressRequestDTO;
import dominos.model.dto.AddressWithoutUserDTO;
import dominos.model.pojo.Address;
import dominos.model.pojo.User;
import dominos.model.repository.AddressRepository;
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

    @Autowired
    private AddressRepository addressRepository;

    @PutMapping("/users/{id}/addresses")
    public AddressWithoutUserDTO addAddress(@RequestBody AddressRequestDTO addressRequestDTO,
                                            HttpSession session, @PathVariable int id) {
        User loggedUser = sessionManager.getLoggedUser(session);
        if (loggedUser.getId() != id) {
            throw new BadRequestException("You cannot add address to another user!");
        }

        return addressService.addAddress(addressRequestDTO, id);
    }

    @DeleteMapping("/addresses/{addressId}")
    public AddressWithoutUserDTO deleteAddress(@PathVariable int addressId, HttpSession session) {
        if (!sessionManager.validateLogged(session)){
            throw new AuthenticationException("You have to log in!");
        }

        User loggedUser = sessionManager.getLoggedUser(session);

        return addressService.deleteAddress(addressId, loggedUser);
    }

    @GetMapping("/users/{id}/addresses")
    public List<AddressWithoutUserDTO> getAllAddressesByUserId(@PathVariable int id, HttpSession session) {
        User loggedUser = sessionManager.getLoggedUser(session);
        if (loggedUser.getId() != id) {
            throw new BadRequestException("You cannot see addresses of another user!");
        }

        return addressService.getAllAddressesByUserId(id);
    }

    @PostMapping("/users/{userId}/addresses/{addressId}")
    public AddressWithoutUserDTO editAddress(@RequestBody AddressRequestDTO addressRequestDTO, HttpSession session,
                                             @PathVariable int userId, @PathVariable int addressId) {

        User loggedUser = sessionManager.getLoggedUser(session);
        if (loggedUser.getId() != userId) {
            throw new BadRequestException("You cannot edit address of another user!");
        }

        return addressService.editAddress(addressRequestDTO, userId, addressId);
    }

}