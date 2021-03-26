package dominos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class RegisterRequestUserDTO {

    //private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    //private int age;
    //private String address;
    private String email;
}