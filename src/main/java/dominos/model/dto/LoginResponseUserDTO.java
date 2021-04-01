package dominos.model.dto;

import dominos.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponseUserDTO {
    private int id;
    private String firstName;
    private String lastName;

    public LoginResponseUserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}