package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dao.OrderDAO;
import dominos.model.dto.*;
import dominos.model.pojo.*;
import dominos.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    DoughRepository doughRepository;

    @Autowired
    PizzaSizeRepository pizzaSizeRepository;

    @Autowired
    private AdditionalProductOrderRepository additionalProductOrderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderDAO orderDAO;

    @Transactional
    public void payOrder(RequestOrderDTO requestOrderDTO, Map<IProduct, Integer> cart, User user) {
        if (cart.isEmpty()) {
            throw new BadRequestException("Cart is empty!");
        }

        Optional<Address> address = addressRepository.findById(requestOrderDTO.getAddressId());
        if (address.isEmpty()) {
            throw new NotFoundException("Address doesn't exist!");
        }

        OrderDTO orderDTO = new OrderDTO(user, address.get(), requestOrderDTO.getComment());
        Order order = new Order(orderDTO);
        order = orderRepository.save(order);

        for (Map.Entry<IProduct, Integer> product : cart.entrySet()) {
            if (product.getKey().isPizza()) {

                PizzaOrderDTO pizzaOrderDTO = (PizzaOrderDTO) product.getKey();
                int quantity = product.getValue();
                PizzaOrder pizzaOrder = new PizzaOrder();
                pizzaOrder.setOrder(order);
                Pizza pizza = pizzaRepository.findById(pizzaOrderDTO.getPizza().getId()).get();
                pizzaOrder.setPizza(pizza);
                pizzaOrder.setQuantity(quantity);
                pizzaOrder.setPrice(pizzaOrderDTO.getPrice());
                Dough dough = doughRepository.findById(pizzaOrderDTO.getDough().getId()).get();
                pizzaOrder.setDough(dough);
                PizzaSize pizzaSize = pizzaSizeRepository.findById(pizzaOrderDTO.getPizzaSize().getId()).get();
                pizzaOrder.setSize(pizzaSize);
                pizzaOrder.setModifications(pizzaOrderDTO.getModifications());
                pizzaOrder = pizzaOrderRepository.save(pizzaOrder);
            } else {
                AdditionalProductOrderDTO additionalProductOrderDTO =
                        new AdditionalProductOrderDTO(order, (AdditionalProductDTO) product.getKey(),
                                product.getKey().getPrice(), product.getValue());

                AdditionalProductOrder additionalProductOrder =
                        new AdditionalProductOrder(additionalProductOrderDTO);

                additionalProductOrder = additionalProductOrderRepository.save(additionalProductOrder);
            }
        }
    }

    public Map<Integer, Map<LocalDate, List<String>>> getAllMadeOrdersByUserId(int userId) throws SQLException {
        return orderDAO.getAllMadeOrdersByUserId(userId);
    }
}
