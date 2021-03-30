package dominos.controller;

import dominos.exceptions.AuthenticationException;
import dominos.model.dto.IngredientDTO;
import dominos.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class IngredientController extends AbstractController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/ingredients")
    public List<IngredientDTO> getAll(HttpSession session) {
        if (!sessionManager.validateLogged(session)) {
            throw new AuthenticationException("You have to log in!");
        }
        return ingredientService.getAllIngredients();
    }
}
