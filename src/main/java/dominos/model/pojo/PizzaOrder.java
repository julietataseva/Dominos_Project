package dominos.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders_have_pizzas")
public class PizzaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
    private int quantity;
    private double price;
    private String modifications;
    @ManyToOne
    @JoinColumn(name = "dough_type_id")
    private Dough dough;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private PizzaSize size;

    public PizzaOrder(Order order, Pizza pizza, int quantity, double price, String modifications, Dough dough, PizzaSize size){
        this.order = order;
        this.pizza = pizza;
        this.quantity = quantity;
        this.price = price;
        this.modifications = modifications;
        this.dough = dough;
        this.size = size;
    }
}