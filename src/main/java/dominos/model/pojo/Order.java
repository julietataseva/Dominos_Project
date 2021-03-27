package dominos.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    @JsonBackReference
    private User owner;
    private LocalDate createdAt;
    @OneToOne
    private Address address;
    private String comment;
    @OneToMany(mappedBy = "order")
    private List<PizzaOrder> pizzaOrders;
    @OneToMany(mappedBy = "order")
    private List<AdditionalProductOrder> additionalProductOrders;
}
