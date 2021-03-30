package dominos.model.pojo;

import dominos.model.dto.PizzaOrderDTO;
import dominos.model.dto.PizzaOrderWithoutIngredientsDTO;
import dominos.model.repository.DoughRepository;
import dominos.model.repository.PizzaRepository;
import dominos.model.repository.PizzaSizeRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
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
}