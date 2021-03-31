package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.model.pojo.Image;
import dominos.model.repository.ImageRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public byte[] download(int imageId) throws IOException {
        if (imageId <= 0) {
            throw new BadRequestException("Id must be a positive number!");
        }

        Optional<Image> imageOptional = imageRepository.findById(imageId);
        if(imageOptional.isEmpty()){
            throw new BadRequestException("This image doesn't exist!");
        }

        Image image = imageOptional.get();
        String url = image.getUrl();
        File pFile = new File(url);
        byte[] array = Files.readAllBytes(pFile.toPath());
        return Files.readAllBytes(pFile.toPath());
    }
}