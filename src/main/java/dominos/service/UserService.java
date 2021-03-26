package dominos.service;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.EditRequestUserDTO;
import dominos.model.dto.EditResponseUserDTO;
import dominos.exceptions.AuthenticationException;
import dominos.model.dto.LoginUserDTO;
import dominos.model.dto.RegisterRequestUserDTO;
import dominos.model.dto.RegisterResponseUserDTO;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.UserWithoutPasswordDTO;
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

    public EditResponseUserDTO editUser(EditRequestUserDTO userDTO, int id) {
        //todo check if the passed id is the same as the logged user's id
        User user = userRepository.findById(id);
        if(user == null){
            throw new NotFoundException("User with id " + id + " not found.");
        }


    }

    public UserWithoutPasswordDTO login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null){
            throw new AuthenticationException("Wrong credentials");
        }else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(loginUserDTO.getPassword(),user.getPassword())){
                return new UserWithoutPasswordDTO(user);
            }else {
                throw new AuthenticationException("Wrong credentials");
            }
        }
    }

    /*
    public List<UserWithoutPassDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithoutPassDTO> returnUsers = new ArrayList<>();
        for(User u : users){
            returnUsers.add(new UserWithoutPassDTO(u));
        }
        return returnUsers;
    }

    public UserWithoutPassDTO getUserById(int id) {
        Optional<User> schrodingerUser = userRepository.findById(id);
        if(schrodingerUser.isPresent()){
            return new UserWithoutPassDTO(schrodingerUser.get());
        }
        else{
            throw new NotFoundException("User not found");
        }
    }

    public UserWithoutPassDTO login(LoginUserDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if(user == null){
            throw new AuthenticationException("Wrong credentials");
        }
        else{
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(dto.getPassword(), user.getPassword())){
                return new UserWithoutPassDTO(user);
            }
            else{
                throw new AuthenticationException("Wrong credentials");
            }
        }
    }

    @Transactional
    public UserWithoutPassDTO buyCar(int userId, int carId) {
        Optional<User> sUser = userRepository.findById(userId);
        Optional<Car> sCar = carRepository.findById(carId);
        if(!sUser.isPresent()){
            throw new NotFoundException("User not found");
        }
        if(!sCar.isPresent()){
            throw new NotFoundException("Car not found");
        }
        Car car = sCar.get();
        User user = sUser.get();
        if(car.getOwner() != null){
            throw new BadRequestException("Car already bought");
        }
        car.setOwner(user);
        carRepository.save(car);//update cars set owner_id = 15 where id = 1
        return new UserWithoutPassDTO(userRepository.findById(userId).get());
    }

     */

}
