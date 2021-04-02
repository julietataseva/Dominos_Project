package dominos.model.dto.user_dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class EditRequestUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}