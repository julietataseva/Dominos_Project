package dominos.model.pojo;

import dominos.model.dto.IngredientDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    @ManyToMany(mappedBy = "ingredients")
    private List<Pizza> pizzas; //pizzas that contain the ingredient

    public Ingredient(IngredientDTO ingredientDTO) {
        this.id = ingredientDTO.getId();
        this.name = ingredientDTO.getName();
        this.pizzas = new ArrayList<>();
    }
}