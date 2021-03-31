package dominos.controller;

import dominos.service.ImageService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController extends AbstractController{
    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/images/{id}", produces = "image/*") //MediaType.IMAGE_JPEG_VALUE
    public @ResponseBody
    byte[] download(@PathVariable int id) throws IOException {
        return imageService.download(id);
    }
}