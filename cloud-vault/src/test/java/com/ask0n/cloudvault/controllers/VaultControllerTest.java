package com.ask0n.cloudvault.controllers;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.Role;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.UserRole;
import com.ask0n.cloudvault.entities.Vault;
import com.ask0n.cloudvault.repositories.UserRepository;
import com.ask0n.cloudvault.repositories.VaultRepository;
import com.ask0n.cloudvault.security.JwtProvider;
import com.ask0n.cloudvault.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class VaultControllerTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VaultRepository vaultRepository;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    public void init() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .username("Ask0n")
                .password("$2a$12$EfqPR9wcrsBB2PkZi4T1zuDPI9j9nyO7W2l1QjoZl23tjDdu5NgHq").build();
        final Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.builder().user(user).role(Role.builder().role("ROLE_USER").build()).build());
        user.setUserRoles(userRoles);
        token = "Bearer " + jwtProvider.generateToken(user);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.checkToken(any(String.class))).thenReturn(true);

        final Vault vault = Vault.builder()
                .id(UUID.randomUUID())
                .user(user)
                .filename("test.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .data("test".getBytes())
                .deleted(false).build();

        when(vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(vault.getFilename(), user.getId()))
                .thenReturn(Optional.of(vault));

        final Vault vault1 = Vault.builder()
                .id(UUID.randomUUID())
                .user(user)
                .filename("test1.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .size(12)
                .data("test1".getBytes())
                .deleted(false).build();

        final Vault vault2 = Vault.builder()
                .id(UUID.randomUUID())
                .user(user)
                .filename("test2.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .size(12)
                .data("test2".getBytes())
                .deleted(false).build();

        final Vault vault3 = Vault.builder()
                .id(UUID.randomUUID())
                .user(user)
                .filename("test3.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .size(12)
                .data("test3".getBytes())
                .deleted(false).build();

        final List<Vault> vaultList = Arrays.asList(vault1, vault2, vault3);

        when(vaultRepository.findAllByUser_IdAndDeletedFalse(user.getId())).thenReturn(Optional.of(vaultList));
    }

    @Test
    public void fileUploadTest200() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes());

        mockMvc.perform(multipart("/file")
                        .file(file)
                        .header("auth-token", token))
                .andExpect(status().isOk());
    }

    @Test
    public void fileUploadTest400() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "test",
                "test.txt",
                "text/plain",
                "fdf".getBytes());

        mockMvc.perform(multipart("/file")
                        .file(file)
                        .header("auth-token", token))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fileUploadTest401() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes());

        mockMvc.perform(multipart("/file")
                        .file(file)
                        .header("auth-token", token + "123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void fileGetTest200() throws Exception {
        final String filename = "test.txt";

        mockMvc.perform(get("/file")
                        .header("auth-token", token)
                        .param("filename", filename))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    public void fileGetTest404() throws Exception {
        final String filename = " ";

        mockMvc.perform(get("/file")
                        .header("auth-token", token)
                        .param("filename", filename))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void fileGetTest401() throws Exception {
        final String filename = "test.txt";

        mockMvc.perform(get("/file")
                        .header("auth-token", token + "123")
                        .param("filename", filename))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void fileDeleteTest200() throws Exception {
        final String filename = "test.txt";

        mockMvc.perform(delete("/file")
                        .header("auth-token", token)
                        .param("filename", filename))
                .andExpect(status().isOk());
    }

    @Test
    public void fileDeleteTest404() throws Exception {
        final String filename = " ";

        mockMvc.perform(delete("/file")
                        .header("auth-token", token)
                        .param("filename", filename))
                .andExpect(status().isNotFound());
    }

    @Test
    public void fileDeleteTest401() throws Exception {
        final String filename = "test.txt";

        mockMvc.perform(delete("/file")
                        .header("auth-token", token + "123")
                        .param("filename", filename))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void filePutTest200() throws Exception {
        final String filename = "test.txt";
        final String newFilename = "test1.txt";

        mockMvc.perform(put("/file")
                        .header("auth-token", token)
                        .param("filename", filename)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \" " + newFilename + "\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void filePutTest400() throws Exception {
        final String filename = "test.txt";
        final String newFilename = "test1.txt";

        mockMvc.perform(put("/file")
                        .header("auth-token", token)
                        .param("filename", filename)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"new\": \" " + newFilename + "\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void filePutTest401() throws Exception {
        final String filename = "test.txt";
        final String newFilename = "test1.txt";

        mockMvc.perform(put("/file")
                        .header("auth-token", token + "123")
                        .param("filename", filename)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \" " + newFilename + "\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getListTest200() throws Exception {
        final int limit = 2;

        mockMvc.perform(get("/list")
                        .header("auth-token", token)
                        .param("limit", String.valueOf(limit)))
                .andExpect(content().json("[ {\"filename\": \"test1.txt\", \"size\": 12}," +
                        " {\"filename\": \"test2.txt\", \"size\": 12} ]"))
                .andExpect(status().isOk());
    }

    @Test
    public void getListTest400() throws Exception {
        final int limit = -1;

        mockMvc.perform(get("/list")
                        .header("auth-token", token)
                        .param("limit", String.valueOf(limit)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getListTest401() throws Exception {
        final int limit = 2;

        mockMvc.perform(get("/list")
                        .header("auth-token", token + "123")
                        .param("limit", String.valueOf(limit)))
                .andExpect(status().isUnauthorized());
    }

}
