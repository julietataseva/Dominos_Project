package dominos.utils;

import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.model.dto.user_dto.EditRequestUserDTO;
import dominos.model.pojo.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

public abstract class Validator {
    private static final int MIN_PASSWORD_LENGTH = 6;

    public static void validatePhoneNumber(String phoneNumber) {
        String validatePhoneNumber = "^([+]?359)|0?(|-| )8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}$";
        if (phoneNumber == null || !phoneNumber.matches(validatePhoneNumber)) {
            throw new BadRequestException("Invalid phone number!");
        }
    }

    public static void validateLatitude(String latitude) {
        String latitudeMatch = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";

        if (latitude == null || !Pattern.matches(latitudeMatch, latitude)) {
            throw new BadRequestException("Invalid latitude!");
        }
    }

    public static void validateLongitude(String longitude) {
        String longitudeMatch = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";

        if (longitude == null || !Pattern.matches(longitudeMatch, longitude)) {
            throw new BadRequestException("Invalid longitude!");
        }
    }

    public static  void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new BadRequestException("Invalid description!");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty() ||
                password.length() < MIN_PASSWORD_LENGTH || containsOnlySpaces(password)) {
            throw new BadRequestException("Invalid password!");
        }
    }

    private static boolean containsOnlySpaces(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) != ' ') {
                return false;
            }
        }

        return true;
    }

    public static void validateConfirmPassword(String confirmPassword, String initialPassword) {
        if (!initialPassword.equals(confirmPassword)) {
            throw new BadRequestException("Passwords don't match!");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Invalid name!");
        }
    }

    public static void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null || !pattern.matcher(email).matches()) {
            throw new BadRequestException("Invalid email!");
        }
    }
    public static void validateNewEmail(User user, String newEmail) {
        if (newEmail != null) {
            validateEmail(newEmail);
            user.setEmail(newEmail);
        }
    }

    public static void validateNewFirstName(User user, String firstName) {
        if (firstName != null) {
            if (firstName.isEmpty()) {
                throw new BadRequestException("First name should not be empty!");
            }
            user.setFirstName(firstName);
        }
    }

    public static void validateNewLastName(User user, String lastName) {
        if (lastName != null) {
            if (lastName.isEmpty()) {
                throw new BadRequestException("Last name should not be empty!");
            }
            user.setLastName(lastName);
        }
    }

    public static void validateCurrentAndNewPassword(User user, EditRequestUserDTO userDTO) {
        String currentPassword = userDTO.getCurrentPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (currentPassword != null) {
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new AuthenticationException("Wrong credentials!");
            }

            String newPassword = userDTO.getNewPassword();
            Validator.validatePassword(newPassword);

            String confirmPassword = userDTO.getConfirmPassword();
            validateConfirmPassword(confirmPassword, newPassword);

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
        }
    }
}