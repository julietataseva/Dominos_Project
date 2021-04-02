package dominos.service;

import dominos.model.dto.*;
import dominos.exceptions.AuthenticationException;
import dominos.model.dto.ResponseUserDTO;
import dominos.exceptions.BadRequestException;
import dominos.model.pojo.User;
import dominos.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final int MIN_PASSWORD_LENGTH = 6;

    public ResponseUserDTO addUser(RegisterRequestUserDTO userDTO) {
        String email = userDTO.getEmail();
        this.validateEmail(email);

        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email already exists");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String initialPassword = userDTO.getPassword();
        this.validatePassword(initialPassword);

        String confirmPassword = userDTO.getConfirmPassword();
        this.validateConfirmPassword(confirmPassword, initialPassword);

        String encodedPassword = encoder.encode(initialPassword);
        userDTO.setPassword(encodedPassword);

        String firstName = userDTO.getFirstName();
        validateName(firstName);

        String lastName = userDTO.getLastName();
        validateName(lastName);

        User user = new User(userDTO);
        user = userRepository.save(user);
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(user);
        return responseUserDTO;
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty() ||
                password.length() < MIN_PASSWORD_LENGTH || this.containsOnlySpaces(password)) {
            throw new BadRequestException("Invalid password!");
        }
    }

    private boolean containsOnlySpaces(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) != ' ') {
                return false;
            }
        }

        return true;
    }

    private void validateConfirmPassword(String confirmPassword, String initialPassword) {
        if (!initialPassword.equals(confirmPassword)) {
            throw new BadRequestException("Passwords don't match!");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Invalid name!");
        }
    }

    public ResponseUserDTO editUser(EditRequestUserDTO userDTO, User loggedUser) {
        String newFirstName = userDTO.getFirstName();
        this.validateNewFirstName(loggedUser, newFirstName);

        String newLastName = userDTO.getLastName();
        this.validateNewLastName(loggedUser, newLastName);

        String newEmail = userDTO.getEmail();
        this.validateNewEmail(loggedUser, newEmail);

        this.validateCurrentAndNewPassword(loggedUser, userDTO);

        loggedUser = userRepository.save(loggedUser);
        return new ResponseUserDTO(loggedUser);
    }

    private void validateNewEmail(User user, String newEmail) {
        if (newEmail != null) {
            this.validateEmail(newEmail);
            user.setEmail(newEmail);
        }
    }

    private void validateNewFirstName(User user, String firstName) {
        if (firstName != null) {
            if (firstName.isEmpty()) {
                throw new BadRequestException("First name should not be empty!");
            }
            user.setFirstName(firstName);
        }
    }

    private void validateNewLastName(User user, String lastName) {
        if (lastName != null) {
            if (lastName.isEmpty()) {
                throw new BadRequestException("Last name should not be empty!");
            }
            user.setLastName(lastName);
        }
    }

    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null || !pattern.matcher(email).matches()) {
            throw new BadRequestException("Invalid email!");
        }
    }

    private void validateCurrentAndNewPassword(User user, EditRequestUserDTO userDTO) {
        String currentPassword = userDTO.getCurrentPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (currentPassword != null) {
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new AuthenticationException("Wrong credentials!");
            }

            String newPassword = userDTO.getNewPassword();
            this.validatePassword(newPassword);

            String confirmPassword = userDTO.getConfirmPassword();
            this.validateConfirmPassword(confirmPassword, newPassword);

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
        }
    }

    public ResponseUserDTO login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null) {
            throw new AuthenticationException("Wrong credentials");
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
                return new ResponseUserDTO(user);
            } else {
                throw new AuthenticationException("Wrong credentials");
            }
        }
    }

    public SuccessDTO deleteUser(int id) {
        userRepository.deleteById(id);
        return new SuccessDTO("You have successfully deleted your account");
    }
}