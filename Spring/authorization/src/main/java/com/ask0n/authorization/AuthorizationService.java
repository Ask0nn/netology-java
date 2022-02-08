package com.ask0n.authorization;

import com.ask0n.exception.UnauthorizedUser;
import com.ask0n.user.User;
import com.ask0n.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {
    UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Authorities> getAuthorities(User user) {
        return userRepository.getUserAuthorities(user)
                .orElseThrow(() -> new UnauthorizedUser(user.getUsername() + " not found"))
                .getAuthorities();
    }
}
