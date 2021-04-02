package dominos.model.dto.address_dto;

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
    private String latitude;
    private String longitude;
    private String description;

    public AddressWithoutUserDTO(Address address) {
        this.id = address.getId();
        this.phoneNumber = address.getPhoneNumber();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        this.description = address.getDescription();
    }
}