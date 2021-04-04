package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.model.pojo.PizzaImage;
import dominos.model.repository.PizzaImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class PizzaImageService {
    @Autowired
    private PizzaImageRepository pizzaImageRepository;

    public byte[] download(int imageId) throws IOException {
        Optional<PizzaImage> imageOptional = pizzaImageRepository.findById(imageId);
        if (imageOptional.isEmpty()) {
            throw new BadRequestException("This image doesn't exist!");
        }

        PizzaImage pizzaImage = imageOptional.get();
        String url = pizzaImage.getUrl();
        File physicalFile = new File(url);
        return Files.readAllBytes(physicalFile.toPath());
    }
}