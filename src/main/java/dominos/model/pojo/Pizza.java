package dominos.model.pojo;

import dominos.model.dto.ingredient_dto.IngredientDTO;
import dominos.model.dto.pizza_dto.PizzaAddedToCartDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@NoArgsConstructor
@Component
@Getter
@Setter
@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    @ManyToMany
    @JoinTable(
            name = "pizzas_have_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
    @OneToMany(mappedBy = "pizza")
    private List<PizzaOrder> pizzaOrders;
    @OneToMany(mappedBy = "pizza")
    private List<PizzaImage> pizzaImages;

    public Pizza() {
        this.ingredients = new ArrayList<>();
        this.pizzaOrders = new ArrayList<>();
        this.pizzaImages = new ArrayList<>();
    }

    public Pizza(PizzaAddedToCartDTO pizzaAddedToCartDTO) {
        this.id = pizzaAddedToCartDTO.getId();
        this.name = pizzaAddedToCartDTO.getName();
        this.price = pizzaAddedToCartDTO.getPrice();
        this.ingredients = new ArrayList<>();
        for (IngredientDTO ingredientDTO : pizzaAddedToCartDTO.getIngredients()) {
            this.ingredients.add(new Ingredient(ingredientDTO));
        }
    }
}