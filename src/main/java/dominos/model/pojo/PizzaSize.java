package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="pizza_sizes")
public class PizzaSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Size size;
    @OneToMany(mappedBy = "size_id")
    private List<PizzaOrder> pizzaOrders;

    public enum Size{
        MEDIUM, LARGE, JUMBO
    }
}