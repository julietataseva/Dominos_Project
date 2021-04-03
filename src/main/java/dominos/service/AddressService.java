package dominos.service;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dto.address_dto.AddressRequestDTO;
import dominos.model.dto.address_dto.AddressWithoutUserDTO;
import dominos.model.pojo.Address;
import dominos.model.pojo.User;
import dominos.model.repository.AddressRepository;
import dominos.utils.Validator;
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

    public AddressWithoutUserDTO addAddress(AddressRequestDTO addressRequestDTO, User loggedUser) {
        String phoneNumber = addressRequestDTO.getPhoneNumber();
        Validator.validatePhoneNumber(phoneNumber);

        String latitude = addressRequestDTO.getLatitude();
        Validator.validateLatitude(latitude);

        String longitude = addressRequestDTO.getLongitude();
        Validator.validateLongitude(longitude);

        String description = addressRequestDTO.getDescription();
        Validator.validateDescription(description);

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
            throw new BadRequestException("This address doesn't exits!");
        }

        String newPhoneNumber = addressRequestDTO.getPhoneNumber();
        if (newPhoneNumber != null) {
            Validator.validatePhoneNumber(newPhoneNumber);
            address.setPhoneNumber(newPhoneNumber);
        }

        String newLatitude = addressRequestDTO.getLatitude();
        if (newLatitude != null) {
            Validator.validateLatitude(newLatitude);
            address.setLatitude(newLatitude);
        }

        String newLongitude = addressRequestDTO.getLongitude();
        if (newLongitude != null) {
            Validator.validateLongitude(newLongitude);
            address.setLongitude(newLongitude);
        }

        String newDescription = addressRequestDTO.getDescription();
        if (newDescription != null) {
            Validator.validateDescription(newDescription);
            address.setDescription(newDescription);
        }

        address = addressRepository.save(address);
        return new AddressWithoutUserDTO(address);
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

    public AddressWithoutUserDTO chooseAddressForCurrentOrder(int addressId, User loggedUser) {
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isEmpty() || address.get().getUser().getId() != loggedUser.getId()) {
            throw new NotFoundException("Address does not exist!");
        }

        return new AddressWithoutUserDTO(address.get());
    }
}