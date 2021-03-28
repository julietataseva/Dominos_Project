package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.model.dto.AddressRequestDTO;
import dominos.model.dto.AddressWithoutUserDTO;
import dominos.model.pojo.Address;
import dominos.model.pojo.User;
import dominos.model.repository.AddressRepository;
import dominos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public AddressWithoutUserDTO addAddress(AddressRequestDTO addressRequestDTO, int id){
        User user = userRepository.findById(id).get();

        String validatePhoneNumber = "^([+]?359)|0?(|-| )8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}$";
        String phoneNumber = addressRequestDTO.getPhoneNumber();
        if(phoneNumber == null || !phoneNumber.matches(validatePhoneNumber)){
            throw new BadRequestException("Invalid phone number!");
        }

        String latitude = addressRequestDTO.getLatitude();
        if(latitude == null || latitude.equals("")){
            throw new BadRequestException("Invalid latitude!");
        }

        String longitude = addressRequestDTO.getLongitude();
        if(longitude == null || longitude.equals("")){
            throw new BadRequestException("Invalid longitude!");
        }

        String description = addressRequestDTO.getDescription();
        if(description == null || description.equals("")){
            throw new BadRequestException("Invalid description!");
        }

        Address address = new Address(addressRequestDTO);
        address.setUser(user);
        address = addressRepository.save(address);
        return new AddressWithoutUserDTO(address);
    }
}