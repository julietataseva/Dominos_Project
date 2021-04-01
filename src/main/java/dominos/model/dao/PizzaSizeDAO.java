package dominos.model.dao;

import dominos.model.dto.DoughDTO;
import dominos.model.dto.PizzaSizeDTO;
import dominos.model.pojo.Dough;
import dominos.model.pojo.PizzaSize;
import dominos.model.repository.PizzaSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class PizzaSizeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;

    private static final String GET_MOST_PREFERRED_PIZZA_SIZE =
            "SELECT ps.id AS most_preferred, COUNT(ps.id) AS count\n" +
                    "FROM pizza_sizes ps\n" +
                    "JOIN orders_have_pizzas ohp\n" +
                    "ON ps.id = size_id\n" +
                    "GROUP BY ps.id\n" +
                    "ORDER BY count DESC;\n" +
                    "\n";


    public PizzaSizeDTO getMostPreferredPizzaSize() {
        PizzaSizeDTO pizzaSizeDTO = null;
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MOST_PREFERRED_PIZZA_SIZE)) {

            ResultSet rows = preparedStatement.executeQuery();
            rows.next();
            int pizzaSizeId = rows.getInt(1);
            Optional<PizzaSize> optionalPizzaSize = pizzaSizeRepository.findById(pizzaSizeId);
            pizzaSizeDTO = new PizzaSizeDTO(optionalPizzaSize.get());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return pizzaSizeDTO;
    }
}
