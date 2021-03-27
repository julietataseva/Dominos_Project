package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    private int id;
    private String name;
    private double price;
    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private PizzaSize size;
    @ManyToOne
    @JoinColumn(name = "dough_type_id", nullable = false)
    private Dough dough;
}