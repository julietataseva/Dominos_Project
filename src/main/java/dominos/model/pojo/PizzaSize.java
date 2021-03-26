package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PizzaSize {
    private int id;
    private Size size;

    public enum Size{
        MEDIUM, BIG, JUMBO
    }
}