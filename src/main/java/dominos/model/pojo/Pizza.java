package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Component
public class Pizza {
    private int id;
    private String name;
    private double price;
    private PizzaSize size;
    private Dough dough;
}