package com.ask0n.cloudvault.repositories;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.JwtTokens;
import com.ask0n.cloudvault.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JwtRepositoryTest {
    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByUserIdTest() {
        final String token = "TEST TOKEN";
        final User user = userRepository.findByUsername("Ask0n").get();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        final UUID userId = entityManager.persist(user).getId();
        entityManager.persist(jwtToken);

        Optional<JwtTokens> savedToken = jwtRepository.findByUser_Id(userId);

        assertThat(savedToken).isPresent();
        assertThat(savedToken.get().getToken()).isEqualTo(token);
    }

    @Test
    public void findByTokenTest() {
        final String token = "TEST TOKEN";
        final User user = userRepository.findByUsername("Ask0n").get();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        entityManager.persist(user);
        entityManager.persist(jwtToken);

        final Optional<JwtTokens> savedToken = jwtRepository.findByToken(token);

        assertThat(savedToken).isPresent();
        assertThat(savedToken.get().getToken()).isEqualTo(token);
    }

    @Test
    public void deleteByTokenTest() {
        final String token = "TEST TOKEN";
        final User user = userRepository.findByUsername("Ask0n").get();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        entityManager.persist(user);
        entityManager.persist(jwtToken);

        Optional<JwtTokens> savedToken = jwtRepository.findByToken(token);

        assertThat(savedToken).isPresent();
        assertThat(savedToken.get().getToken()).isEqualTo(token);

        jwtRepository.deleteByToken(token);

        savedToken = jwtRepository.findByToken(token);

        assertThat(savedToken).isNotPresent();
    }
}
