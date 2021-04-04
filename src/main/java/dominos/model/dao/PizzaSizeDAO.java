package dominos.model.dao;

import dominos.model.dto.pizza_dto.PizzaSizeDTO;
import dominos.model.pojo.PizzaSize;
import dominos.model.repository.PizzaSizeRepository;
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
public class PizzaSizeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;

    private static final String GET_MOST_PREFERRED_PIZZA_SIZE =

            "SELECT ps.id AS most_preferred, SUM(ohp.quantity) AS quantity\n" +
                    "FROM pizza_sizes ps\n" +
                    "JOIN orders_have_pizzas ohp\n" +
                    "ON ps.id = ohp.size_id\n" +
                    "GROUP BY ps.id\n" +
                    "HAVING quantity = (SELECT SUM(quantity) AS quantity\n" +
                    "\t\t\tFROM orders_have_pizzas\n" +
                    "      GROUP BY size_id\n" +
                    "      ORDER BY quantity DESC\n" +
                    "      LIMIT 1);";


    public List<PizzaSizeDTO> getMostPreferredPizzaSizes() throws SQLException {
        List<PizzaSizeDTO> mostPreferredPizzaSizes = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MOST_PREFERRED_PIZZA_SIZE)) {

            ResultSet rows = preparedStatement.executeQuery();
            while (rows.next()) {
                int pizzaSizeId = rows.getInt(1);
                Optional<PizzaSize> optionalPizzaSize = pizzaSizeRepository.findById(pizzaSizeId);
                PizzaSizeDTO pizzaSizeDTO = new PizzaSizeDTO(optionalPizzaSize.get());
                mostPreferredPizzaSizes.add(pizzaSizeDTO);
            }

            return mostPreferredPizzaSizes;
        }
    }
}