package dominos.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Component
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pizza_images")
public class PizzaImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
}