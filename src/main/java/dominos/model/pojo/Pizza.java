package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
public class Pizza {
    private int id;
    private String name;
    private double price;

    private PizzaSize size;
    private Dough dough;
}