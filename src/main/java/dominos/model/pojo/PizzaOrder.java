package dominos.model.pojo;

import dominos.model.dto.PizzaOrderDTO;
import dominos.model.dto.PizzaOrderWithoutIngredientsDTO;
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
    private int quantity;
    private double price;
    private String modifications;
    @ManyToOne
    @JoinColumn(name = "dough_type_id")
    private Dough dough;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private PizzaSize size;

    public PizzaOrder(Order order, PizzaOrderDTO pizzaOrderDTO, int quantity){
        this.order = order;
        this.pizza = new Pizza(pizzaOrderDTO.getPizza());
        this.quantity = quantity;
        this.price = pizzaOrderDTO.getPrice();
        this.dough = new Dough(pizzaOrderDTO.getDough());
        this.size = new PizzaSize(pizzaOrderDTO.getPizzaSize());
    }
}