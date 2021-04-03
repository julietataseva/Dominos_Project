package dominos.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
public class OrderDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_MADE_ORDERS_BY_USER_ID =
            "SELECT o.id, o.created_at, p.name FROM orders AS o\n" +
                    "JOIN orders_have_pizzas AS ohp\n" +
                    "ON o.id = ohp.order_id\n" +
                    "JOIN pizzas AS p\n" +
                    "ON ohp.pizza_id = p.id\n" +
                    "UNION\n" +
                    "SELECT o.id, o.created_at, ap.name FROM orders AS o\n" +
                    "JOIN orders_have_additional_products AS ohap\n" +
                    "ON o.id = ohap.order_id\n" +
                    "JOIN additional_products AS ap\n" +
                    "ON ohap.additional_product_id = ap.id\n" +
                    "WHERE o.owner_id = ?\n" +
                    "ORDER BY created_at DESC;";

    public Map<Integer, Map<LocalDate, List<String>>> getAllMadeOrdersForLoggedUser(int userId) throws SQLException {
        Map<Integer, Map<LocalDate, List<String>>> orders = new TreeMap<>(Collections.reverseOrder());

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_MADE_ORDERS_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                LocalDate orderDate = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();
                String productName = resultSet.getString("name");

                if (!orders.containsKey(orderId)) {
                    orders.put(orderId, new HashMap<>());
                }

                if (!orders.get(orderId).containsKey(orderDate)) {
                    orders.get(orderId).put(orderDate, new ArrayList<>());
                }

                orders.get(orderId).get(orderDate).add(productName);
            }
        }
        return orders;
    }
}