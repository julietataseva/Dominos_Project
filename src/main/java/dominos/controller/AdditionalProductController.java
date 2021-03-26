package dominos.controller;

import dominos.model.dto.AdditionalProductDTO;
import dominos.service.AdditionalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AdditionalProductController extends AbstractController {

    @Autowired
    private AdditionalProductService additionalProductService;

    @GetMapping("/menu/products/{name}")
    public AdditionalProductDTO getAdditionalProductByName(@PathVariable String name){
        return additionalProductService.getAdditionalProductByName(name);
    }

    @GetMapping("/menu/products")
    public List<AdditionalProductDTO> getAdditionalProductsMenu(){
        return additionalProductService.getAdditionalProductMenu();
    }
}
