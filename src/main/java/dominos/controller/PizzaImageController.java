package dominos.controller;

import dominos.service.PizzaImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PizzaImageController extends AbstractController{
    @Autowired
    private PizzaImageService imageService;

    @GetMapping(value = "/pizzas/images/{id}", produces = "image/*")
    public @ResponseBody
    byte[] download(@PathVariable int id) throws IOException {
        return imageService.download(id);
    }
}