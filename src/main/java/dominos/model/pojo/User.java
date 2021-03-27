package dominos.model.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dominos.model.dto.RegisterRequestUserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    @OneToMany(mappedBy = "owner")
    private List<Order> orders;

    public User(RegisterRequestUserDTO userDTO){
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        password = userDTO.getPassword();
        email = userDTO.getEmail();
    }
}