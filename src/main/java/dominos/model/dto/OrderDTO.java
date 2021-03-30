package dominos.model.dto;

import dominos.model.pojo.Address;
import dominos.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class OrderDTO {
    private User owner;
    private Address address;
    private String comment;
}
