package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.model.pojo.AdditionalProduct;
import dominos.model.pojo.AdditionalProductImage;
import dominos.model.pojo.PizzaImage;
import dominos.model.repository.AdditionalProductImageRepository;
import dominos.model.repository.AdditionalProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class AdditionalProductImageService {
    @Autowired
    private AdditionalProductImageRepository additionalProductImageRepository;

    public byte[] download(int imageId) throws IOException {
        Optional<AdditionalProductImage> imageOptional = additionalProductImageRepository.findById(imageId);
        if(imageOptional.isEmpty()){
            throw new BadRequestException("This image doesn't exist!");
        }

        AdditionalProductImage additionalProductImage = imageOptional.get();
        String url = additionalProductImage.getUrl();
        File pFile = new File(url);
        return Files.readAllBytes(pFile.toPath());
    }
}