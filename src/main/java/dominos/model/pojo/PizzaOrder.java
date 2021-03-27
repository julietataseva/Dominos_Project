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
    private Order order;
    @OneToOne
    private Pizza pizza;
    @ManyToOne
    private Dough dough;
    @ManyToOne
    private PizzaSize size;
}
