package com.ask0n.user;

import com.ask0n.authorization.Authorities;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList(Arrays.asList(
            new User("Ask0n", "1234", Arrays.asList(Authorities.READ, Authorities.WRITE, Authorities.DELETE)),
            new User("S", "0011", Collections.singletonList(Authorities.READ)),
            new User("validTest", "1", Collections.emptyList())));

    public Optional<User> getUserAuthorities(User user) {
        return users.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))
                .findFirst();
    }
}
