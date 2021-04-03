package dominos.model.dto.address_dto;

import dominos.model.pojo.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Component
public class AddressRequestDTO {
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String description;

    public AddressRequestDTO(Address address){
        this.phoneNumber = address.getPhoneNumber();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        this.description = address.getDescription();
    }
}