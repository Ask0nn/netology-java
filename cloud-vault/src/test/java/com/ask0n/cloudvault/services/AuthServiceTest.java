package com.ask0n.cloudvault.services;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.exceptions.NotFoundException;
import com.ask0n.cloudvault.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
public class AuthServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void findByUsernameTest() {
        final User user = User.builder()
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        final User userFromService = authService.findByUsername(user.getUsername());

        assertThat(userFromService.getUsername()).isEqualTo(user.getUsername());
        assertThat(userFromService.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void findByNotCorrectUsernameTest() {
        final User user = User.builder()
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        NotFoundException e = assertThrows(
                NotFoundException.class,
                () -> authService.findByUsername(user.getUsername() + "1")
        );

        assertThat(e).hasMessageContaining("not found");
    }

    @Test
    public void findByUsernameAndPasswordTest() {
        final User user = User.builder()
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("TEST", user.getPassword())).thenReturn(true);

        final User userFromService = authService.findByUsernameAndPassword(user.getUsername(), "TEST");

        assertThat(userFromService.getUsername()).isEqualTo(user.getUsername());
        assertThat(userFromService.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void findByNotCorrectUsernameAndPasswordTest() {
        final User user = User.builder()
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("TEST", user.getPassword())).thenReturn(true);

        NotFoundException e = assertThrows(
                NotFoundException.class,
                () -> authService.findByUsernameAndPassword(user.getUsername() + "1", "TEST")
        );

        assertThat(e).hasMessageContaining("not found");
    }

    @Test
    public void findByUsernameAndNotCorrectPasswordTest() {
        final User user = User.builder()
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("TEST", user.getPassword())).thenReturn(true);

        BadCredentialsException e = assertThrows(
                BadCredentialsException.class,
                () -> authService.findByUsernameAndPassword(user.getUsername(), "TEST1")
        );

        assertThat(e).hasMessageContaining("Bad credentials");
    }

}
