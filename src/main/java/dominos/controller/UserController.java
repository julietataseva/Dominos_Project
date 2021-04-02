package dominos.controller;

import dominos.model.dto.*;
import dominos.model.dto.RegisterAndEditResponseUserDTO;
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
    public RegisterAndEditResponseUserDTO register(@RequestBody RegisterRequestUserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/users/login")
    public LoginResponseUserDTO login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        LoginResponseUserDTO loginResponseUserDTO = userService.login(loginUserDTO);
        sessionManager.loginUser(session, loginResponseUserDTO.getId());
        return loginResponseUserDTO;
    }

    @PostMapping("/users/logout")
    public SuccessDTO logout(HttpSession session) {
        sessionManager.validateLogged(session);
        sessionManager.logoutUser(session);
        return new SuccessDTO("Logging out successful");
    }

    @PostMapping("/users")
    public RegisterAndEditResponseUserDTO edit(@RequestBody EditRequestUserDTO userDTO, HttpSession session) {
        User loggedUser = sessionManager.getLoggedUser(session);
        return userService.editUser(userDTO, loggedUser);
    }

    @DeleteMapping("/users")
    public SuccessDTO delete(HttpSession session) {
        sessionManager.validateLogged(session);
        int userId = sessionManager.getLoggedUser(session).getId();
        SuccessDTO response = userService.deleteUser(userId);
        sessionManager.logoutUser(session);
        return response;
    }
}