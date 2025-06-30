package com.nata.blue.service;

import static java.util.stream.Collectors.toSet;

import com.nata.blue.model.User;
import com.nata.blue.model.UserFilter;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private long nextId = 1;

    public @Nullable User get(long id) {
        return getUserOrThrow(id);
    }

    public Set<User> getUsers(@NonNull UserFilter filter, @Nullable Integer page, @Nullable Integer pageSize) {
        validate(page, pageSize);

        if (page == null) {
            return users.values().stream()
                .filter(filter::test)
                .collect(toSet());
        }
        return users.values().stream()
            .filter(filter::test)
            .skip((long) page * pageSize)
            .limit(pageSize)
            .collect(toSet());
    }

    public User create(@NonNull User user) {
        validate(user);
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    public User patch(long id, @NonNull User patch) {
        User user = getUserOrThrow(id);
        user.patch(patch);
        return user;
    }

    public void delete(long id) {
        getUserOrThrow(id);
        users.remove(id);
    }

    private User getUserOrThrow(long id) {
        if (!users.containsKey(id)) {
            throw new NoSuchElementException("user with id "+ id +" not found");
        }
        return users.get(id);
    }

    private void validate(User user) {
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new InvalidParameterException("First name is empty!");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new InvalidParameterException("Last name is empty!");
        }
    }

    private void validate(Integer page, Integer pageSize) {
        if (page == null && pageSize == null) {
            return;
        }
        if (page == null) {
            throw new InvalidParameterException("Page number is null!");
        }
        if (pageSize == null) {
            throw new InvalidParameterException("Page size is null!");
        }
        if (page < 0) {
            throw new InvalidParameterException("Page number must be >= 0: " + page);
        }
        if (pageSize <= 0) {
            throw new InvalidParameterException("Page size must be > 0: " + pageSize);
        }
    }
}
