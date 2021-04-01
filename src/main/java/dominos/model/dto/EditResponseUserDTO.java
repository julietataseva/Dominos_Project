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
public class EditResponseUserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public EditResponseUserDTO(User user) {
        id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        email = user.getEmail();
    }
}