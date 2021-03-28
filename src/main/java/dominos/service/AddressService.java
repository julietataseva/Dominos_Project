package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.exceptions.NoContentException;
import dominos.exceptions.NotFoundException;
import dominos.model.dto.AddressRequestDTO;
import dominos.model.dto.AddressWithoutUserDTO;
import dominos.model.pojo.Address;
import dominos.model.pojo.User;
import dominos.model.repository.AddressRepository;
import dominos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public AddressWithoutUserDTO addAddress(AddressRequestDTO addressRequestDTO, int id){
        User user = userRepository.findById(id).get();

        String phoneNumber = addressRequestDTO.getPhoneNumber();
        this.isValidPhoneNumber(phoneNumber);

        String latitude = addressRequestDTO.getLatitude();
        this.isValidLatitude(latitude);

        String longitude = addressRequestDTO.getLongitude();
        this.isValidLongitude(longitude);

        String description = addressRequestDTO.getDescription();
        this.isValidDescription(description);

        Address address = new Address(addressRequestDTO);
        address.setUser(user);
        address = addressRepository.save(address);
        return new AddressWithoutUserDTO(address);
    }

    public List<AddressWithoutUserDTO> getAllAddressesByUserId(int id) throws NoContentException{
        User user = userRepository.findById(id).get();
        List<Address> addresses = user.getAddresses();
        if(addresses.size() == 0) {
            throw new NoContentException("You don't have any addresses added.");
        }

        List<AddressWithoutUserDTO> returnAddresses = new ArrayList<>();
        for(Address address : addresses){
            returnAddresses.add(new AddressWithoutUserDTO(address));
        }

        return returnAddresses;
    }

    public AddressWithoutUserDTO editAddress(AddressRequestDTO addressRequestDTO, int userId, int addressId) {
        Address address = addressRepository.findById(addressId).get();
        if(address == null){
            throw new BadRequestException("Address with id " + addressId + " doesn't exits!");
        }

        User user = userRepository.findById(userId).get();
        List<Address> userAddresses = user.getAddresses();
        if(!userAddresses.contains(address)){
            throw new BadRequestException("You cannot edit address that is not yours!");
        }

        String newPhoneNumber = addressRequestDTO.getPhoneNumber();
        if(newPhoneNumber != null){
            this.isValidPhoneNumber(newPhoneNumber);
            address.setPhoneNumber(newPhoneNumber);
        }

        String newLatitude = addressRequestDTO.getLatitude();
        if(newLatitude != null){
            this.isValidLatitude(newLatitude);
            address.setLatitude(newLatitude);
        }

        String newLongitude = addressRequestDTO.getLongitude();
        if(newLongitude != null){
            this.isValidLongitude(newLongitude);
            address.setLongitude(newLongitude);
        }

        String newDescription = addressRequestDTO.getDescription();
        if(newDescription != null){
            this.isValidDescription(newDescription);
            address.setDescription(newDescription);
        }

        address = addressRepository.save(address);
        return new AddressWithoutUserDTO(address);
    }

    private boolean isValidPhoneNumber(String phoneNumber){
        String validatePhoneNumber = "^([+]?359)|0?(|-| )8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}$";
        if(phoneNumber == null || !phoneNumber.matches(validatePhoneNumber)){
            throw new BadRequestException("Invalid phone number!");
        }

        return true;
    }

    private boolean isValidLatitude(String latitude){
        if(latitude == null || latitude.equals("")){
            throw new BadRequestException("Invalid latitude!");
        }

        return true;
    }

    private boolean isValidLongitude(String longitude){
        if(longitude == null || longitude.equals("")){
            throw new BadRequestException("Invalid longitude!");
        }

        return true;
    }

    private boolean isValidDescription(String description){
        if(description == null || description.equals("")){
            throw new BadRequestException("Invalid description!");
        }

        return true;
    }
}