package com.ask0n.cloudvault.testcontainers;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.JwtTokens;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.Vault;
import com.ask0n.cloudvault.repositories.JwtRepository;
import com.ask0n.cloudvault.repositories.UserRepository;
import com.ask0n.cloudvault.repositories.VaultRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class ApplicationTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private VaultRepository vaultRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() throws Exception {
        final MvcResult authResponse = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\": \"Ask0n\", \"password\": \"1234\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String body = authResponse.getResponse().getContentAsString();
        final JsonNode tokenNode = objectMapper.readTree(body);
        token = "Bearer " + tokenNode.path("auth-token").asText();

        final User user = userRepository.findByUsername("Ask0n").get();
        final Vault vault1 = Vault.builder()
                .user(user)
                .filename("test1.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .size(1L)
                .data("test1".getBytes())
                .deleted(false)
                .build();
        final Vault vault2 = Vault.builder()
                .user(user)
                .filename("test2.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .size(2L)
                .data("test2".getBytes())
                .deleted(false)
                .build();
        final Vault vault3 = Vault.builder()
                .user(user)
                .filename("test3.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .size(3L)
                .data("test3".getBytes())
                .deleted(true)
                .build();

        vaultRepository.save(vault1);
        vaultRepository.save(vault2);
        vaultRepository.save(vault3);
    }

    @AfterEach
    public void deInit() {
        vaultRepository.deleteAll();
        jwtRepository.deleteAll();
    }

    @Test
    public void loginTest() throws Exception {
        final MvcResult response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\": \"Ask0n\", \"password\": \"1234\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String body = response.getResponse().getContentAsString();
        final JsonNode node = objectMapper.readTree(body);
        final String token = node.path("auth-token").asText();
        final Optional<JwtTokens> jwtToken = jwtRepository.findByToken(token);

        assertThat(jwtToken.isPresent()).isTrue();
        assertThat(jwtToken.get().getUser().getUsername()).isEqualTo("Ask0n");
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(post("/logout")
                        .header("auth-token", "Bearer " + token))
                .andExpect(status().isOk());

        final Optional<JwtTokens> jwtToken = jwtRepository.findByToken(token);

        assertThat(jwtToken.isPresent()).isFalse();
    }

    @Test
    public void getListTest() throws Exception {
        final MvcResult response = mockMvc.perform(get("/list")
                        .header("auth-token", token)
                        .param("limit", "3"))
                .andExpect(status().isOk())
                .andReturn();

        final List<Vault> vaults = objectMapper.readValue(response.getResponse().getContentAsString(),
                new TypeReference<List<Vault>>() {});
        assertThat(vaults.size()).isEqualTo(2);
        assertThat(vaults.get(0).getFilename()).isEqualTo("test1.txt");
    }

    @Test
    public void postFileTest() throws Exception {
        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "test4.txt",
                "text/plain",
                "test4".getBytes());

        mockMvc.perform(multipart("/file")
                        .file(file)
                        .header("auth-token", token))
                .andExpect(status().isOk());

        final UUID userId = userRepository.findByUsername("Ask0n").get().getId();
        final Optional<Vault> vault = vaultRepository
                .findByFilenameAndDeletedFalseAndUser_Id(file.getOriginalFilename(), userId);

        assertThat(vault.isPresent()).isTrue();
        assertThat(vault.get().getUser().getUsername()).isEqualTo("Ask0n");
    }

    @Test
    public void getFileTest() throws Exception {
        final String filename = "test1.txt";

        final MvcResult response = mockMvc.perform(get("/file")
                        .header("auth-token", token)
                        .param("filename", filename))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andReturn();

        final UUID userId = userRepository.findByUsername("Ask0n").get().getId();
        final Optional<Vault> vault = vaultRepository
                .findByFilenameAndDeletedFalseAndUser_Id(filename, userId);

        assertThat(vault.get().getData()).isEqualTo(response.getResponse().getContentAsByteArray());
    }

    @Test
    public void putFileTest() throws Exception {
        final String filename = "test1.txt";
        final String newFilename = "test.txt";

        mockMvc.perform(put("/file")
                        .header("auth-token", token)
                        .param("filename", filename)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \" " + newFilename + "\" }"))
                .andExpect(status().isOk());

        final UUID userId = userRepository.findByUsername("Ask0n").get().getId();
        final Optional<Vault> vault = vaultRepository
                .findByFilenameAndDeletedFalseAndUser_Id(newFilename, userId);

        assertThat(vault.isPresent()).isTrue();
        assertThat(vault.get().getFilename()).isNotEqualTo(filename);
    }

    @Test
    public void deleteFile() throws Exception {
        final UUID userId = userRepository.findByUsername("Ask0n").get().getId();
        final UUID vaultId = vaultRepository.findByFilenameAndDeletedFalseAndUser_Id("test1.txt", userId)
                .get().getId();

        final String filename = "test1.txt";

        mockMvc.perform(delete("/file")
                        .header("auth-token", token)
                        .param("filename", filename))
                .andExpect(status().isOk());

        final Optional<Vault> vault = vaultRepository.findById(vaultId);

        assertThat(vault.isPresent()).isTrue();
        assertThat(vault.get().isDeleted()).isTrue();
    }
}
