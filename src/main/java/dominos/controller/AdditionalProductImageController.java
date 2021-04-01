package dominos.controller;

import dominos.model.pojo.AdditionalProductImage;
import dominos.service.AdditionalProductImageService;
import dominos.service.PizzaImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AdditionalProductImageController extends AbstractController{
    @Autowired
    private AdditionalProductImageService imageService;

    @GetMapping(value = "/products/images/{id}", produces = "image/*")
    public @ResponseBody
    byte[] download(@PathVariable int id) throws IOException {
        return imageService.download(id);
    }
}