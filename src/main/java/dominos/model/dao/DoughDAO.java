package dominos.model.dao;

import dominos.model.dto.DoughDTO;
import dominos.model.pojo.Dough;
import dominos.model.repository.DoughRepository;
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
public class DoughDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DoughRepository doughRepository;

    private static final String GET_MOST_PREFERRED_DOUGH =
            "SELECT dt.id AS most_preferred, SUM(ohp.quantity) AS quantity\n" +
                    "                    FROM dough_types dt\n" +
                    "                    JOIN orders_have_pizzas ohp\n" +
                    "                    ON dt.id = ohp.dough_type_id\n" +
                    "                    GROUP BY dt.id\n" +
                    "                    HAVING quantity = (SELECT SUM(quantity) AS quantity\n" +
                    "\t\t\t\t\t\t\t\t\tFROM orders_have_pizzas\n" +
                    "                                    GROUP BY dough_type_id\n" +
                    "                                    ORDER BY quantity DESC\n" +
                    "                                    LIMIT 1);";


    public List<DoughDTO> getMostPreferredDoughs() throws SQLException {
        List<DoughDTO> mostPreferredDoughs = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MOST_PREFERRED_DOUGH)) {

            ResultSet rows = preparedStatement.executeQuery();
            while (rows.next()) {
                int doughId = rows.getInt(1);
                Optional<Dough> optionalDough = doughRepository.findById(doughId);
                DoughDTO dough = new DoughDTO(optionalDough.get());
                mostPreferredDoughs.add(dough);
            }

            return mostPreferredDoughs;
        }
    }
}