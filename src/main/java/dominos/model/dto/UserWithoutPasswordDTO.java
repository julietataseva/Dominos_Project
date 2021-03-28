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
public class UserWithoutPasswordDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressWithoutUserDTO> addresses;

    public UserWithoutPasswordDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.addresses = new ArrayList<>();
        for (Address address : user.getAddresses()) {
            addresses.add(new AddressWithoutUserDTO(address));
        }
    }
}