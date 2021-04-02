package dominos.controller;

import dominos.exceptions.NotFoundException;
import dominos.model.dto.order_dto.RequestOrderDTO;
import dominos.model.dto.IProductDTO;
import dominos.model.dto.SuccessDTO;
import dominos.model.pojo.User;
import dominos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController extends AbstractController {
    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Map<Integer, Map<LocalDate, List<String>>> getAllMadeOrdersForLoggedUser(HttpSession session) throws SQLException {
        sessionManager.validateLogged(session);
        User user = sessionManager.getLoggedUser(session);
        return orderService.getAllMadeOrdersForLoggedUser(user.getId());
    }

    @PostMapping("/checkout")
    public SuccessDTO payOrder(@RequestBody RequestOrderDTO requestOrderDTO, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProductDTO, Integer> cart = sessionManager.getCartAttribute(session);

        User user = sessionManager.getLoggedUser(session);
        if (sessionManager.getAddressIdAttribute(session) == null) {
            throw new NotFoundException("Please first choose an address for the order!");
        }
        int addressId = sessionManager.getAddressIdAttribute(session);
        orderService.payOrder(requestOrderDTO, addressId, cart, user);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        sessionManager.emptyCart(session);
        return new SuccessDTO("Payment successful");
    }
}