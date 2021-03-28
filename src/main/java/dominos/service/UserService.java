package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.EditRequestUserDTO;
import dominos.model.dto.EditResponseUserDTO;
import dominos.exceptions.AuthenticationException;
import dominos.model.dto.LoginUserDTO;
import dominos.model.dto.RegisterRequestUserDTO;
import dominos.model.dto.RegisterResponseUserDTO;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.LoginResponseUserDTO;
import dominos.model.pojo.User;
import dominos.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public RegisterResponseUserDTO addUser(RegisterRequestUserDTO userDTO){
        if(userRepository.findByEmail(userDTO.getEmail()) != null){
            throw new BadRequestException("Email already exists");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String initialPassword = userDTO.getPassword();
        if(initialPassword == null || initialPassword.equals("")){
            throw new BadRequestException("Invalid password!");
        }

        String confirmPassword = userDTO.getConfirmPassword();
        if(confirmPassword == null || confirmPassword.equals("") ||
                !initialPassword.equals(confirmPassword)){
            throw new BadRequestException("Passwords don't match!");
        }

        String encodedPassword = encoder.encode(initialPassword);
        userDTO.setPassword(encodedPassword);
        User user = new User(userDTO);
        user = userRepository.save(user);
        RegisterResponseUserDTO responseUserDTO = new RegisterResponseUserDTO(user);
        return responseUserDTO;
    }


    public EditResponseUserDTO editUser(EditRequestUserDTO userDTO, int id) {
        Optional<User> u = userRepository.findById(id);
        if(!u.isPresent()){
            throw new NotFoundException("User with id " + id + " not found.");
        }

        User user = u.get();
        String newFirstName = userDTO.getFirstName();
        this.validateNewFirstName(user, newFirstName);

        String newLastName = userDTO.getLastName();
        this.validateNewLastName(user, newLastName);

        String newEmail = userDTO.getEmail();
        this.validateNewEmail(user, newEmail);

        String currentPassword = userDTO.getCurrentPassword();
        this.validateCurrentAndNewPassword(user, currentPassword, userDTO);

        userRepository.save(user);
        user = userRepository.findById(id).get();
        return new EditResponseUserDTO(user);
    }

    private void validateNewFirstName(User user, String newFirstName){
        if(newFirstName != null){
            if(newFirstName.equals("")){
                throw new BadRequestException("First name should not be empty!");
            }
            user.setFirstName(newFirstName);
        }
    }

    private void validateNewLastName(User user, String newLastName) {
        if(newLastName != null){
            if(newLastName.equals("")){
                throw new BadRequestException("Last name should not be empty!");
            }
            user.setLastName(newLastName);
        }
    }

    private void validateNewEmail(User user, String newEmail) {
        if(newEmail != null){
            if(newEmail.equals("")){
                throw new BadRequestException("Email should not be empty!");
            }
            user.setEmail(newEmail);
        }
    }

    private void validateCurrentAndNewPassword(User user, String currentPassword, EditRequestUserDTO userDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(currentPassword != null){
            if(!passwordEncoder.matches(currentPassword, user.getPassword())){
                throw new AuthenticationException("Wrong credentials!");
            }

            String newPassword = userDTO.getNewPassword();
            if(newPassword == null || newPassword.equals("")){
                throw new BadRequestException("New password should not be empty!");
            }

            String confirmPassword = userDTO.getConfirmPassword();
            if(confirmPassword == null || confirmPassword.equals("")){
                throw new BadRequestException("Confirm password should not be empty!");
            }

            if(!newPassword.equals(confirmPassword)){
                throw new BadRequestException("Passwords don't match");
            }

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
        }
    }

    public LoginResponseUserDTO login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null){
            throw new AuthenticationException("Wrong credentials");
        }else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(loginUserDTO.getPassword(),user.getPassword())){
                return new LoginResponseUserDTO(user);
            }else {
                throw new AuthenticationException("Wrong credentials");
            }
        }
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "You have successfully deleted your account";
    }
}