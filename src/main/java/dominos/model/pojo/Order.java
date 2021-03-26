package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class Order {
    private int id;
    private User owner;
    private LocalDate createdAt;
    private Address address;
    private String comment;
}