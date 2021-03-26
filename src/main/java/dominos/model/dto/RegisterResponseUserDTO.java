package dominos.model.dto;

import dominos.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@Component
public class RegisterResponseUserDTO {

    private int id;
    //private String username;
    private String firstName;
    private String lastName;
    //private int age;
    //private String address;
    private String email;
    //List<Car> cars;

    public RegisterResponseUserDTO(User user){
        id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        //username = user.getUsername();
        //age = user.getAge();
        //address = user.getAddress();
        email = user.getEmail();
        //cars = user.getCars();
    }
}