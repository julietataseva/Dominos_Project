package dominos.service;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dto.*;
import dominos.model.pojo.*;
import dominos.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;

    @Autowired
    private AdditionalProductOrderRepository additionalProductOrderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public void payOrder(RequestOrderDTO requestOrderDTO, Map<IProduct, Integer> cart, User user) {
        if (cart.isEmpty()){
            throw new BadRequestException("Cart is empty!");
        }

        Optional<Address> address = addressRepository.findById(requestOrderDTO.getAddressId());
        if (address.isEmpty()) {
            throw new NotFoundException("Address doesn't exist!");
        }

        OrderDTO orderDTO = new OrderDTO(user, address.get(), requestOrderDTO.getComment());
        Order order = new Order(orderDTO);
        order = orderRepository.save(order);

        // TODO save products/pizzas
        for (Map.Entry<IProduct, Integer> product : cart.entrySet()) {
//            if (product.getKey().isPizza()) {
//                PizzaOrderWithoutIngredientsDTO pizzaOrderWithoutIngredientsDTO =
//                        new PizzaOrderWithoutIngredientsDTO(order, product.getKey());
//
//                PizzaOrder pizzaOrder = new PizzaOrder();
//
//                pizzaOrderRepository.save(pizzaOrder);
//            } else {
                AdditionalProductOrderDTO additionalProductOrderDTO =
                        new AdditionalProductOrderDTO(order, (AdditionalProductDTO) product.getKey(),
                                product.getKey().getPrice(), product.getValue());

                AdditionalProductOrder additionalProductOrder =
                        new AdditionalProductOrder(additionalProductOrderDTO);

                additionalProductOrderRepository.save(additionalProductOrder);
//            }
        }
    }
}
