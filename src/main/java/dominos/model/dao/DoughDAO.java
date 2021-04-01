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
import java.util.Optional;

@Component
public class DoughDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DoughRepository doughRepository;

    private static final String GET_MOST_PREFERRED_DOUGH =
            "SELECT dt.id AS most_preferred, COUNT(dt.id) AS count\n" +
                    "FROM dough_types dt\n" +
                    "JOIN orders_have_pizzas ohp\n" +
                    "ON dt.id = ohp.dough_type_id\n" +
                    "GROUP BY dt.id\n" +
                    "ORDER BY count DESC;\n";


    public DoughDTO getMostPreferredDough() {
        DoughDTO doughDTO = null;
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MOST_PREFERRED_DOUGH)) {

            ResultSet rows = preparedStatement.executeQuery();
            rows.next();
            int doughId = rows.getInt(1);
            Optional<Dough> optionalDough = doughRepository.findById(doughId);
            doughDTO = new DoughDTO(optionalDough.get());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doughDTO;
    }
}