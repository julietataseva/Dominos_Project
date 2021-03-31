package dominos.model.dao;

import dominos.model.dto.PizzaResponseDTO;
import dominos.model.pojo.Pizza;
import dominos.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PizzaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PizzaRepository pizzaRepository;

    private static final String GET_MOST_SOLD_PIZZAS =
            "SELECT ohp.pizza_id AS most_sold_pizzas, SUM(ohp.quantity) AS quantity \n" +
                    "FROM orders_have_pizzas AS ohp\n" +
                    "GROUP BY ohp.pizza_id\n" +
                    "HAVING quantity = (\n" +
                    "\tSELECT SUM(p.quantity) FROM orders_have_pizzas AS p \n" +
                    "\tGROUP BY p.pizza_id \n" +
                    "    LIMIT 1)\n" +
                    "ORDER BY quantity DESC;";

    public List<PizzaResponseDTO> getMostSoldPizzas() throws SQLException {
        List<PizzaResponseDTO> mostSoldPizzas = new ArrayList<>();

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MOST_SOLD_PIZZAS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Optional<Pizza> pizza = pizzaRepository.findById(resultSet.getInt("most_sold_pizzas"));
                PizzaResponseDTO pizzaResponseDTO = new PizzaResponseDTO(pizza.get());
                mostSoldPizzas.add(pizzaResponseDTO);
            }
        }
        return mostSoldPizzas;
    }
}
