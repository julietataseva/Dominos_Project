package dominos.service;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Value("${file.path}")
    private String filePath;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    @ResponseBody
    public Image upload(MultipartFile file, int productId) throws IOException {
        if (!Validator.isValidInteger(productId)) {
            throw new BadRequestException("You must enter integer greater than 0!");
        }
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found! Wrong product_id!");
        }
        File imageFile = new File(filePath + File.separator + productId + "_" + System.nanoTime() + ".jpg");
        try (OutputStream os = new FileOutputStream(imageFile)) {
            os.write(file.getBytes());
            Image image = new Image();
            image.setUrl(imageFile.getAbsolutePath());
            image.setProduct(productRepository.findById(productId));
            imageRepository.save(image);
            return image;
        }
    }

    public byte[] download(int imageId) throws IOException {
        if (!Validator.isValidInteger(imageId)) {
            throw new BadRequestException("You must enter integer greater than 0!");
        }
        Image image = imageRepository.getById(imageId);
        String url = image.getUrl();
        File pFile = new File(url);
        return Files.readAllBytes(pFile.toPath());
    }
}