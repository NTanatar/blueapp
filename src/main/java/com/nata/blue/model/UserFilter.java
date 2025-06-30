package com.nata.blue.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFilter {
    private String firstNameContains;
    private String lastNameContains;
    private String emailContains;
    private Date dateOfBirthFrom;
    private Date dateOfBirthTo;
    private String countryContains;
    private Integer postalCodeEquals;

    public boolean test(User user) {
        if (firstNameContains != null && !user.getFirstName().contains(firstNameContains)) {
            return false;
        }
        if (lastNameContains != null && !user.getLastName().contains(lastNameContains)) {
            return false;
        }
        if (emailContains != null && user.getEmail() != null && !user.getEmail().contains(emailContains)) {
            return false;
        }
        if (dateOfBirthFrom != null && user.getDateOfBirth() != null && user.getDateOfBirth().before(dateOfBirthFrom)) {
            return false;
        }
        if (dateOfBirthTo != null && user.getDateOfBirth() != null && user.getDateOfBirth().after(dateOfBirthTo)) {
            return false;
        }
        if (countryContains != null && user.getCountry() != null && !user.getCountry().contains(countryContains)) {
            return false;
        }
        return postalCodeEquals == null || user.getPostalCode() == null || user.getPostalCode().equals(postalCodeEquals);
    }
}
