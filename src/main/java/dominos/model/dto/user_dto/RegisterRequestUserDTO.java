package dominos.model.dto.user_dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class RegisterRequestUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}