package dominos.model.dao;

import dominos.model.dto.LoginResponseUserDTO;
import dominos.model.dto.PizzaResponseDTO;
import dominos.model.pojo.Pizza;
import dominos.model.pojo.User;
import dominos.model.repository.PizzaRepository;
import dominos.model.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    private static final String GET_MOST_SOLD_PIZZAS =
            "SELECT ohp.pizza_id AS most_sold_pizzas, SUM(ohp.quantity) AS quantity \n" +
                    "FROM orders_have_pizzas AS ohp\n" +
                    "GROUP BY ohp.pizza_id\n" +
                    "HAVING quantity = (\n" +
                    "\tSELECT SUM(p.quantity) FROM orders_have_pizzas AS p \n" +
                    "\tGROUP BY p.pizza_id \n" +
                    "    LIMIT 1)\n" +
                    "ORDER BY quantity DESC;";

    private static final String GET_USER_WITH_MOST_PIZZA_ORDERS =
            "SELECT u.id, u.first_name, u.last_name, COUNT(owner_id) as orders_count, SUM(price) as sum\n" +
                    "FROM users u\n" +
                    "JOIN orders o\n" +
                    "ON u.id = o.owner_id\n" +
                    "JOIN orders_have_pizzas ohp\n" +
                    "ON o.id = ohp.order_id\n" +
                    "GROUP BY owner_id\n" +
                    "ORDER BY orders_count DESC, sum DESC;";

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

    public LoginResponseUserDTO getUserWithMostPizzaOrders() {
        LoginResponseUserDTO topFan = null;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_WITH_MOST_PIZZA_ORDERS)) {

            ResultSet rows = statement.executeQuery();
            rows.next();
            int topFanId = rows.getInt(1);
            System.out.println("TOP FAN ID = " + topFanId);
            Optional<User> userOptional = userRepository.findById(topFanId);
            topFan = new LoginResponseUserDTO(userOptional.get());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return topFan;
    }
}