package dominos.model.dao;

import dominos.model.dto.IngredientDTO;
import dominos.model.dto.PizzaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PizzaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PizzaResponseDTO> getMenu() throws SQLException {
        List<PizzaResponseDTO> allPizzas = new ArrayList<>();
        String getAllPizzaIds = "SELECT id FROM pizzas";
        String getAllPizzas = "SELECT p.name AS pizza, i.name AS ingredient " +
                "FROM pizzas p " +
                "JOIN pizzas_have_ingredients phi " +
                "ON p.id = phi.pizza_id " +
                "JOIN ingredients i " +
                "ON i.id = phi.ingredient_id " +
                "WHERE p.id = ?";

        ResultSet allIds = null;
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(getAllPizzas);) {
            allIds = statement.executeQuery(getAllPizzaIds);
            while(allIds.next()){
                int crrId = allIds.getInt(1);
                ps.setInt(1, crrId);
                ResultSet pizzaIngredients = ps.executeQuery();
                PizzaResponseDTO pizza = new PizzaResponseDTO();
                List<IngredientDTO> ingredients = pizza.getIngredients();
                while (pizzaIngredients.next()){
                    if(pizza.getName() == null) {
                        pizza.setName(pizzaIngredients.getString("pizza"));
                    }

                    IngredientDTO ingredient = new IngredientDTO();
                    ingredient.setName(pizzaIngredients.getString("ingredient"));
                    ingredients.add(ingredient);
                }
                allPizzas.add(pizza);
            }
        }

        /*

        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(getAllPizzas);){


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

         */

        return allPizzas;
    }
}