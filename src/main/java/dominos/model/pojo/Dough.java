package dominos.model.pojo;

import dominos.model.dto.DoughDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dough_types")
public class Dough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private DoughType type;
    @OneToMany(mappedBy = "dough")
    private List<PizzaOrder> pizzaOrders;

    public enum DoughType {
        TRADITIONAL, ITALIAN_STYLE, THIN_AND_CRISPY, WITH_PHILADELPHIA
    }

    public Dough(){
        this.id = 1;
        this.type = DoughType.TRADITIONAL;
        this.pizzaOrders = new ArrayList<>();
    }
}