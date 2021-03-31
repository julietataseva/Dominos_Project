package dominos.model.dao;

import dominos.model.dto.AdditionalProductDTO;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.repository.AdditionalProductRepository;
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
public class AdditionalProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdditionalProductRepository additionalProductRepository;

    private static final String GET_MOST_SOLD_ADDITIONAL_PRODUCTS =
            "SELECT ohap.additional_product_id AS most_sold_products, SUM(ohap.quantity) AS quantity \n" +
                    "FROM orders_have_additional_products AS ohap\n" +
                    "GROUP BY ohap.additional_product_id\n" +
                    "HAVING quantity = (\n" +
                    "\tSELECT SUM(a.quantity) FROM orders_have_additional_products AS a \n" +
                    "\tGROUP BY a.additional_product_id \n" +
                    "    LIMIT 1)\n" +
                    "ORDER BY quantity DESC;";

    public List<AdditionalProductDTO> getMostSoldAdditionalProducts() throws SQLException {
        List<AdditionalProductDTO> mostSoldAdditionalProducts = new ArrayList<>();

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MOST_SOLD_ADDITIONAL_PRODUCTS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Optional<AdditionalProduct> additionalProduct =
                        additionalProductRepository.findById(resultSet.getInt("most_sold_products"));
                AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());
                mostSoldAdditionalProducts.add(additionalProductDTO);
            }
        }
        return mostSoldAdditionalProducts;
    }
}
