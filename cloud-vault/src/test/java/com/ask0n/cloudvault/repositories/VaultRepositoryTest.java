package com.ask0n.cloudvault.repositories;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.Vault;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VaultRepositoryTest {
    @Autowired
    private VaultRepository vaultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByFilenameAndDeletedFalseAndUserIdTest() {
        final String filename = "test.txt";
        final User user = userRepository.findByUsername("Ask0n").get();
        final Vault vault = Vault.builder()
                .user(user)
                .filename(filename)
                .contentType(MediaType.TEXT_PLAIN.getType())
                .size(1L)
                .data(new byte[20])
                .deleted(false)
                .build();
        final Vault vaultDeleted = Vault.builder()
                .user(user)
                .filename(filename)
                .contentType(MediaType.TEXT_PLAIN.getType())
                .size(1L)
                .data(new byte[20])
                .deleted(true)
                .build();

        final UUID userId = entityManager.persist(user).getId();
        entityManager.persist(vault);
        entityManager.persist(vaultDeleted);

        Optional<Vault> savedVault = vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(filename, userId);

        assertThat(savedVault).isPresent();
        assertThat(savedVault.get().isDeleted()).isFalse();
    }

    @Test
    public void findAllByUserIdAndDeletedFalseTest() {
        final String filename = "test.txt";
        final User user = userRepository.findByUsername("Ask0n").get();
        final Vault vault = Vault.builder()
                .user(user)
                .filename(filename)
                .contentType(MediaType.TEXT_PLAIN.getType())
                .size(1L)
                .data(new byte[20])
                .deleted(false)
                .build();
        final Vault vaultDeleted = Vault.builder()
                .user(user)
                .filename(filename)
                .contentType(MediaType.TEXT_PLAIN.getType())
                .size(1L)
                .data(new byte[20])
                .deleted(true)
                .build();

        final UUID userId = entityManager.persist(user).getId();
        entityManager.persist(vault);
        entityManager.persist(vaultDeleted);

        Optional<List<Vault>> savedVaults = vaultRepository.findAllByUser_IdAndDeletedFalse(userId);

        assertThat(savedVaults).isPresent();
        assertThat(savedVaults.get().size()).isEqualTo(1);
        assertThat(savedVaults.get().stream().findFirst().get().isDeleted()).isFalse();
    }
}
