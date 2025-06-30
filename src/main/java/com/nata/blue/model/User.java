package com.nata.blue.model;

import java.util.Date;
import lombok.Data;
import lombok.NonNull;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private String country;
    private Integer postalCode;

    public void patch(@NonNull User patchUser) {
        if (patchUser.getFirstName() != null) {
            this.firstName = patchUser.getFirstName();
        }
        if (patchUser.getLastName() != null) {
            this.lastName = patchUser.getLastName();
        }
        if (patchUser.getEmail() != null) {
            this.email = patchUser.getEmail();
        }
        if (patchUser.getDateOfBirth() != null) {
            this.dateOfBirth = patchUser.getDateOfBirth();
        }
        if (patchUser.getCountry() != null) {
            this.country = patchUser.getCountry();
        }
        if (patchUser.getPostalCode() != null) {
            this.postalCode = patchUser.getPostalCode();
        }
    }
}
