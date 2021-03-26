package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Address {
    private int id;
    private User user;
    private String phoneNumber;
    private double longitude;
    private double latitude;
    private String description;
}