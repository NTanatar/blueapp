package com.nata.blue.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.nata.blue.model.User;
import com.nata.blue.model.UserFilter;
import com.nata.blue.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations related to users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{user-id}")
    @Operation(summary = "Get user by ID", description = "Retrieves one user by ID")
    public User getUser(@PathVariable("user-id") long id) {
        return userService.get(id);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of users by filters")
    public Set<User> getUsers(UserFilter filter, Integer page, Integer pageSize) {
        return userService.getUsers(filter, page, pageSize);
    }

    @GetMapping("page")
    @Operation(summary = "Get page of users", description = "Retrieves a page of users by filters")
    public Page<User> getPage(UserFilter filter, Pageable pageable) {
        return userService.getPage(filter, pageable);
    }

    @PostMapping
    @Operation(summary = "Create a user")
    @ResponseStatus(CREATED)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @PatchMapping("{user-id}")
    @Operation(summary = "Update a user", description = "update specified fields")
    public User patchUser(@PathVariable("user-id") long id, @RequestBody User user) {
        return userService.patch(id, user);
    }

    @DeleteMapping("{user-id}")
    @Operation(summary = "Delete a user by ID")
    public void deleteUser(@PathVariable("user-id") long id) {
        userService.delete(id);
    }
}
