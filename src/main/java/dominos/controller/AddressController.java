package dominos.controller;

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

    @GetMapping("/users/{id}/addresses")
    public List<AddressWithoutUserDTO> getAllAddressesByUserId(@PathVariable int id, HttpSession session){
        User loggedUser = sessionManager.getLoggedUser(session);
        if (loggedUser.getId() != id) {
            throw new BadRequestException("You cannot see addresses of another user!");
        }

        return addressService.getAllAddressesByUserId(id);
    }
}