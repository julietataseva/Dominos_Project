package dominos.controller;

import dominos.model.dto.IngredientDTO;
import dominos.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController extends AbstractController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public List<IngredientDTO> getAll(){
        return ingredientService.getAllIngredients();
    }
}
