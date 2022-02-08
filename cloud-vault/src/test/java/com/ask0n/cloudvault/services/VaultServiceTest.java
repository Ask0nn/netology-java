package com.ask0n.cloudvault.services;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.Vault;
import com.ask0n.cloudvault.exceptions.NotFoundException;
import com.ask0n.cloudvault.repositories.UserRepository;
import com.ask0n.cloudvault.repositories.VaultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
public class VaultServiceTest {
    private User user;

    @Mock
    private VaultRepository vaultRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VaultService vaultService;

    @BeforeEach
    public void init() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("Ask0n", null, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        user = User.builder()
                .id(UUID.randomUUID())
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    }

    @Test
    public void saveFileTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "text.txt",
                "text.txt",
                MediaType.TEXT_PLAIN.toString(),
                new byte[20]
        );

        when(vaultRepository.save(any(Vault.class))).then((invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        }));

        final Vault vault = vaultService.saveFile(file);

        assertThat(vault.getFilename()).isEqualTo(file.getOriginalFilename());
        assertThat(vault.getContentType()).isEqualTo(file.getContentType());
        assertThat(vault.isDeleted()).isFalse();
    }

    @Test
    public void getFileTest() {
        final String filename = "text.txt";
        final Vault vault = Vault.builder()
                .user(user)
                .filename(filename)
                .build();

        when(vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(filename, user.getId()))
                .thenReturn(Optional.of(vault));

        final Vault _vault = vaultService.getFile(filename);

        assertThat(_vault.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(_vault.getFilename()).isEqualTo(filename);
    }

    @Test
    public void getFileNotCorrectFilenameTest() {
        final String filename = "text.txt";
        final Vault vault = Vault.builder()
                .user(user)
                .filename(filename)
                .build();

        when(vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(filename, user.getId()))
                .thenReturn(Optional.of(vault));


        NotFoundException e = assertThrows(
                NotFoundException.class,
                () -> vaultService.getFile("123" + filename)
        );

        assertThat(e).hasMessageContaining("input data");
    }

    @Test
    public void deleteFileTest() {
        final String filename = "text.txt";
        final Vault vault = Vault.builder()
                .user(user)
                .filename(filename)
                .deleted(false)
                .build();

        when(vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(filename, user.getId()))
                .thenReturn(Optional.of(vault));

        Vault _vault = vaultService.getFile(filename);

        assertThat(_vault.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(_vault.getFilename()).isEqualTo(filename);
        assertThat(_vault.isDeleted()).isFalse();

        vaultService.deleteFile(filename);

        _vault = vaultService.getFile(filename);

        assertThat(_vault.isDeleted()).isTrue();
    }

    @Test
    public void updateFileTest() {
        final String filename = "text.txt";
        final String newFilename = "1text.txt";
        final Vault vault = Vault.builder()
                .user(user)
                .filename(filename)
                .deleted(false)
                .build();

        when(vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(filename, user.getId()))
                .thenReturn(Optional.of(vault));

        Vault _vault = vaultService.getFile(filename);

        assertThat(_vault.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(_vault.getFilename()).isNotEqualTo(newFilename);

        vaultService.updateFilename(filename, newFilename);

        _vault = vaultService.getFile(filename);

        assertThat(_vault.getFilename()).isEqualTo(newFilename);
    }

    @Test
    public void getAllTest() {
        final List<Vault> vaultList = new ArrayList<>();
        final Vault vault1 = Vault.builder()
                .user(user)
                .filename("text1.txt")
                .deleted(false)
                .build();
        final Vault vault2 = Vault.builder()
                .user(user)
                .filename("text2.txt")
                .deleted(false)
                .build();
        vaultList.add(vault1);
        vaultList.add(vault2);

        when(vaultRepository.findAllByUser_IdAndDeletedFalse(user.getId()))
                .thenReturn(Optional.of(vaultList));

        assertThat(vaultList.size()).isEqualTo(2);
    }
}
