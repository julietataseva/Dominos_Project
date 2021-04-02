package dominos.model.pojo;

import dominos.model.dto.address_dto.AddressRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "address")
    private List<Order> orders;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String description;

    public Address(AddressRequestDTO addressRequestDTO) {
        this.phoneNumber = addressRequestDTO.getPhoneNumber();
        this.latitude = addressRequestDTO.getLatitude();
        this.longitude = addressRequestDTO.getLongitude();
        this.description = addressRequestDTO.getDescription();
    }
}