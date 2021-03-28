package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.EditRequestUserDTO;
import dominos.model.dto.EditResponseUserDTO;
import dominos.model.dto.LoginUserDTO;
import dominos.model.dto.RegisterRequestUserDTO;
import dominos.model.dto.RegisterResponseUserDTO;
import dominos.model.dto.LoginResponseUserDTO;
import dominos.model.pojo.User;
import dominos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    @PutMapping("/users")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/users/login")
    public LoginResponseUserDTO login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        LoginResponseUserDTO loginResponseUserDTO = userService.login(loginUserDTO);
        sessionManager.loginUser(session, loginResponseUserDTO.getId());
        return loginResponseUserDTO;
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session) {
        sessionManager.logoutUser(session);
    }

    @PostMapping("/users/{id}")
    public EditResponseUserDTO edit(@RequestBody EditRequestUserDTO userDTO, HttpSession session, @PathVariable int id) {
        User loggedUser = sessionManager.getLoggedUser(session);
        if (loggedUser.getId() != id) {
            throw new BadRequestException("You cannot edit the profile of another user!");
        }

        return userService.editUser(userDTO, id);
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }

        if (sessionManager.getLoggedUser(session).getId() != id) {
            throw new AuthenticationException("You have to be logged in your account");
        }

        return userService.deleteUser(id);
    }
}