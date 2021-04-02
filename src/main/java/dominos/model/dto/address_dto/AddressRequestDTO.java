package dominos.model.dto.address_dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class AddressRequestDTO {
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String description;
}