package dominos.controller;

import dominos.model.dto.RequestOrderDTO;
import dominos.model.pojo.IProduct;
import dominos.model.pojo.User;
import dominos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Map<Integer, Map<LocalDate, List<String>>> getAllMadeOrdersByUserId(@PathVariable int userId,
                                                                               HttpSession session) throws SQLException {
        sessionManager.validateLogged(session);
        return orderService.getAllMadeOrdersByUserId(userId);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> payOrder(@RequestBody RequestOrderDTO requestOrderDTO, HttpSession session) {
        sessionManager.validateLogged(session);
        Map<IProduct, Integer> cart = sessionManager.getCartAttribute(session);

        User user = sessionManager.getLoggedUser(session);
        orderService.payOrder(requestOrderDTO, cart, user);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        sessionManager.emptyCart(session);
        return new ResponseEntity<>("Payment successful", HttpStatus.OK);
    }
}