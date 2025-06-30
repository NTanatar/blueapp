package com.nata.blue.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.nata.blue.model.User;
import com.nata.blue.model.UserFilter;
import com.nata.blue.service.UserService;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{user-id}")
    public User getUser(@PathVariable("user-id") long id) {
        return userService.get(id);
    }

    @GetMapping
    public Set<User> getUsers(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Date birthFrom,
        @RequestParam(required = false) Date birthTo,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) Integer postalCode,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize) {

        UserFilter filter = UserFilter.builder()
            .firstNameContains(firstName)
            .lastNameContains(lastName)
            .emailContains(email)
            .dateOfBirthFrom(birthFrom)
            .dateOfBirthTo(birthTo)
            .countryContains(country)
            .postalCodeEquals(postalCode)
            .build();

        return userService.getUsers(filter, page, pageSize);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @PatchMapping("{user-id}")
    public User patchUser(@PathVariable("user-id") long id, @RequestBody User user) {
        return userService.patch(id, user);
    }

    @DeleteMapping("{user-id}")
    public void deleteUser(@PathVariable("user-id") long id) {
        userService.delete(id);
    }
}
