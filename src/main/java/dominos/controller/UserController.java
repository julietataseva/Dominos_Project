package dominos.controller;

import dominos.model.dto.EditRequestUserDTO;
import dominos.model.dto.EditResponseUserDTO;
import dominos.model.dto.LoginUserDTO;
import dominos.model.dto.RegisterRequestUserDTO;
import dominos.model.dto.RegisterResponseUserDTO;
import dominos.model.dto.UserWithoutPasswordDTO;
import dominos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PutMapping("/users")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = userService.login(loginUserDTO);
        session.setAttribute("LoggedUser", userWithoutPasswordDTO.getId());
        return userWithoutPasswordDTO;
    }

    @PostMapping("/users/{id}")
    public EditResponseUserDTO edit (@RequestBody EditRequestUserDTO userDTO, @PathVariable int id){
       return userService.editUser(userDTO, id);
    }
}