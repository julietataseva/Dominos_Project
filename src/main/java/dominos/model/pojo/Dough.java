package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="dough_types")
public class Dough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private DoughType type;

    public enum DoughType{
        TRADITIONAL, ITALIAN_STYLE, THIN_AND_CRISPY, WITH_PHILADELPHIA
    }
}