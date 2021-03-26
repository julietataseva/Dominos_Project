package dominos.service;

import dominos.model.dto.RegisterRequestUserDTO;
import dominos.model.dto.RegisterResponseUserDTO;
import dominos.exceptions.BadRequestException;
import dominos.model.pojo.User;
import dominos.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        String confirmPassword = userDTO.getConfirmPassword();
        if(!initialPassword.equals(confirmPassword)){
            throw new BadRequestException("Passwords don't match");
        }

        String encodedPassword = encoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        User user = new User(userDTO);
        user = userRepository.save(user);
        RegisterResponseUserDTO responseUserDTO = new RegisterResponseUserDTO(user);
        return responseUserDTO;
    }
}