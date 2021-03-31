package dominos.controller;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController extends AbstractController{
    //@Value(staticConstructor = "${file.path}")
    //private String filePath;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SessionManager sessionManager;

    @GetMapping(value = "/images/{id}", produces = "image/*")
    public @ResponseBody
    byte[] download(@PathVariable int id) throws IOException {
        return imageService.download(id);
    }
}