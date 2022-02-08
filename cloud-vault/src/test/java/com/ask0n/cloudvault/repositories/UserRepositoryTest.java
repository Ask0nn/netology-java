package com.ask0n.cloudvault.repositories;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByUsername() {
        final String username = "Ask0n";

        Optional<User> savedUser = userRepository.findByUsername(username);

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getUsername()).isEqualTo(username);
    }
}
