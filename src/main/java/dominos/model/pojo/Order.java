package dominos.model.pojo;

import dominos.model.dto.OrderDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String comment;
    @OneToMany(mappedBy = "order")
    private List<PizzaOrder> pizzaOrders;
    @OneToMany(mappedBy = "order")
    private List<AdditionalProductOrder> additionalProductOrders;

    public Order(OrderDTO orderDTO) {
        this.owner = orderDTO.getOwner();
        this.createdAt = LocalDate.now();
        this.address = orderDTO.getAddress();
        this.comment = orderDTO.getComment();
    }
}