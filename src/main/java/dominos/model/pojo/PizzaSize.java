package dominos.model.pojo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pizza_sizes")
public class PizzaSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Size size;
    @OneToMany(mappedBy = "size")
    private List<PizzaOrder> pizzaOrders;

    public enum Size {
        MEDIUM, LARGE, JUMBO
    }

    public PizzaSize() {
        this.id = 2;
        this.size = Size.LARGE;
        this.pizzaOrders = new ArrayList<>();
    }
}