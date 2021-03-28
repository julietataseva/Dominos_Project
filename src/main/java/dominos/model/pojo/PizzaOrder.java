package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders_have_pizzas")
public class PizzaOrder {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
    @ManyToOne
    @JoinColumn(name = "dough_type_id")
    private Dough dough;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private PizzaSize size;
}
