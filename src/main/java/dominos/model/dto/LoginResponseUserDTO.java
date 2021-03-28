package dominos.model.dto;

import dominos.model.pojo.Address;
import dominos.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserWithoutPasswordAndAddressDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public UserWithoutPasswordAndAddressDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}