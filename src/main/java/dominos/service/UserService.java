package dominos.service;

import dominos.model.dto.*;
import dominos.exceptions.AuthenticationException;
import dominos.model.dto.user_dto.EditRequestUserDTO;
import dominos.model.dto.user_dto.LoginUserDTO;
import dominos.model.dto.user_dto.ResponseUserDTO;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.user_dto.RegisterRequestUserDTO;
import dominos.model.pojo.User;
import dominos.model.repository.UserRepository;
import dominos.utils.Validator;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseUserDTO addUser(RegisterRequestUserDTO userDTO) {
        String email = userDTO.getEmail();
        Validator.validateEmail(email);

        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email already exists");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String initialPassword = userDTO.getPassword();
        Validator.validatePassword(initialPassword);

        String confirmPassword = userDTO.getConfirmPassword();
        Validator.validateConfirmPassword(confirmPassword, initialPassword);

        String encodedPassword = encoder.encode(initialPassword);
        userDTO.setPassword(encodedPassword);

        String firstName = userDTO.getFirstName();
        Validator.validateName(firstName);

        String lastName = userDTO.getLastName();
        Validator.validateName(lastName);

        User user = new User(userDTO);
        user = userRepository.save(user);
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(user);
        return responseUserDTO;
    }

    public ResponseUserDTO editUser(EditRequestUserDTO userDTO, User loggedUser) {
        String newFirstName = userDTO.getFirstName();
        if (newFirstName != null) {
            Validator.validateName(newFirstName);
            loggedUser.setFirstName(newFirstName);
        }

        String newLastName = userDTO.getLastName();
        if (newLastName != null) {
            Validator.validateName(newLastName);
            loggedUser.setLastName(newLastName);
        }

        String newEmail = userDTO.getEmail();
        if(newEmail != null){
            Validator.validateEmail(newEmail);
            User user = userRepository.findByEmail(newEmail);
            if (user != null){
                throw new BadRequestException("Email already exists!");
            }
            loggedUser.setEmail(newEmail);
        }

        String currentPassword = userDTO.getCurrentPassword();
        if(currentPassword != null) {
            Validator.validateEnteredAndActualPasswords(currentPassword, loggedUser);
        }

        String newPassword = userDTO.getNewPassword();
        String confirmPassword = userDTO.getConfirmPassword();
        Validator.validateNewAndConfirmPassword(newPassword, currentPassword, confirmPassword, loggedUser);

        loggedUser = userRepository.save(loggedUser);
        return new ResponseUserDTO(loggedUser);
    }

    public ResponseUserDTO login(LoginUserDTO loginUserDTO) {
        if(loginUserDTO.getEmail() == null || loginUserDTO.getPassword() == null){
            throw new AuthenticationException("Wrong credentials!");
        }

        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null) {
            throw new AuthenticationException("Wrong credentials!");
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
                return new ResponseUserDTO(user);
            } else {
                throw new AuthenticationException("Wrong credentials!");
            }
        }
    }

    public SuccessDTO deleteUser(int id) {
        userRepository.deleteById(id);
        return new SuccessDTO("You have successfully deleted your account");
    }
}