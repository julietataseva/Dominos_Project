package dominos.model.dto;

import dominos.model.pojo.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class AddressWithoutUserDTO {
    private int id;
    private String phoneNumber;
    private double longitude;
    private double latitude;
    private String description;

    public AddressWithoutUserDTO(Address address) {
        this.id = address.getId();
        this.phoneNumber = address.getPhoneNumber();
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
        this.description = address.getDescription();
    }
}
