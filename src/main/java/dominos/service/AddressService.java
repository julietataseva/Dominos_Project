package dominos.service;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
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
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public AddressWithoutUserDTO addAddress(AddressRequestDTO addressRequestDTO, User loggedUser) {
        String phoneNumber = addressRequestDTO.getPhoneNumber();
        this.validatePhoneNumber(phoneNumber);

        String latitude = addressRequestDTO.getLatitude();
        this.validateLatitude(latitude);

        String longitude = addressRequestDTO.getLongitude();
        this.validateLongitude(longitude);

        String description = addressRequestDTO.getDescription();
        this.validateDescription(description);

        Address address = new Address(addressRequestDTO);
        address.setUser(loggedUser);
        address = addressRepository.save(address);
        return new AddressWithoutUserDTO(address);
    }

    public List<AddressWithoutUserDTO> getAllAddressesOfUser(User loggedUser) {
        List<Address> addresses = loggedUser.getAddresses();
        if (addresses.size() == 0) {
            throw new NotFoundException("You don't have any addresses added.");
        }

        List<AddressWithoutUserDTO> returnAddresses = new ArrayList<>();
        for (Address address : addresses) {
            returnAddresses.add(new AddressWithoutUserDTO(address));
        }

        return returnAddresses;
    }

    public AddressWithoutUserDTO editAddress(AddressRequestDTO addressRequestDTO, User loggedUser, int addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new BadRequestException("This address doesn't exits!");
        }

        Address address = optionalAddress.get();
        List<Address> userAddresses = loggedUser.getAddresses();
        if (!userAddresses.contains(address)) {
            throw new BadRequestException("You cannot edit address that is not yours!");
        }

        String newPhoneNumber = addressRequestDTO.getPhoneNumber();
        if (newPhoneNumber != null) {
            this.validatePhoneNumber(newPhoneNumber);
            address.setPhoneNumber(newPhoneNumber);
        }

        String newLatitude = addressRequestDTO.getLatitude();
        if (newLatitude != null) {
            this.validateLatitude(newLatitude);
            address.setLatitude(newLatitude);
        }

        String newLongitude = addressRequestDTO.getLongitude();
        if (newLongitude != null) {
            this.validateLongitude(newLongitude);
            address.setLongitude(newLongitude);
        }

        String newDescription = addressRequestDTO.getDescription();
        if (newDescription != null) {
            this.validateDescription(newDescription);
            address.setDescription(newDescription);
        }

        address = addressRepository.save(address);
        return new AddressWithoutUserDTO(address);
    }

    private void validatePhoneNumber(String phoneNumber) {
        String validatePhoneNumber = "^([+]?359)|0?(|-| )8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}$";
        if (phoneNumber == null || !phoneNumber.matches(validatePhoneNumber)) {
            throw new BadRequestException("Invalid phone number!");
        }
    }

    private void validateLatitude(String latitude) {
        String latitudeMatch = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";

        if (latitude == null || !Pattern.matches(latitudeMatch, latitude)) {
            throw new BadRequestException("Invalid latitude!");
        }
    }

    private void validateLongitude(String longitude) {
        String longitudeMatch = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";

        if (longitude == null || !Pattern.matches(longitudeMatch, longitude)) {
            throw new BadRequestException("Invalid longitude!");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new BadRequestException("Invalid description!");
        }
    }

    public AddressWithoutUserDTO deleteAddress(int addressId, User loggedUser) {
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isEmpty()) {
            throw new NotFoundException("Address does not exist!");
        }

        AddressWithoutUserDTO addressWithoutUserDTO = new AddressWithoutUserDTO(address.get());
        int addressUserId = address.get().getUser().getId();

        if (addressUserId != loggedUser.getId()) {
            throw new AuthenticationException("You can not delete this address");
        }

        addressRepository.deleteById(addressId);
        return addressWithoutUserDTO;
    }
}