package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private LocalDate createdAt;
    @OneToOne
    private Address address;
    private String comment;
}