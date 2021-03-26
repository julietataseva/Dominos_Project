package dominos.controller;

<<<<<<< HEAD
import dominos.model.dto.LoginUserDTO;
=======
import dominos.model.dto.EditRequestUserDTO;
import dominos.model.dto.EditResponseUserDTO;
>>>>>>> 0fe2a1c0067b162193dcdc020e02510c0e30b8fa
import dominos.model.dto.RegisterRequestUserDTO;
import dominos.model.dto.RegisterResponseUserDTO;
import dominos.model.dto.UserWithoutPasswordDTO;
import dominos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> 0fe2a1c0067b162193dcdc020e02510c0e30b8fa

import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PutMapping("/users")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO userDTO) {
        return userService.addUser(userDTO);
    }

<<<<<<< HEAD
    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = userService.login(loginUserDTO);
        session.setAttribute("LoggedUser", userWithoutPasswordDTO.getId());
        return userWithoutPasswordDTO;
=======
    @PostMapping("/users/{id}")
    public EditResponseUserDTO edit (@RequestBody EditRequestUserDTO userDTO, @PathVariable int id){
       return userService.editUser(id);
>>>>>>> 0fe2a1c0067b162193dcdc020e02510c0e30b8fa
    }
}