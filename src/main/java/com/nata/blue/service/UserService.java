package com.nata.blue.service;

import static java.util.Comparator.comparing;

import com.nata.blue.model.User;
import com.nata.blue.model.UserFilter;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private long nextId = 1;

    public @Nullable User get(long id) {
        return getUserOrThrow(id);
    }

    public Page<User> getPage(@NonNull UserFilter filter, @NonNull Pageable pageable) {
        validate(pageable.getPageNumber(), pageable.getPageSize());

        List<User> filtered = users.values().stream()
            .filter(filter::test)
            .sorted(comparing(User::getId))
            .toList();

        if (filtered.isEmpty() || pageable.isUnpaged()) {
            return new PageImpl<>(filtered);
        }
        List<User> paged = filtered.stream()
            .skip((long) pageable.getPageNumber() * pageable.getPageSize())
            .limit(pageable.getPageSize())
            .toList();
        return new PageImpl<>(paged, pageable, filtered.size());
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
