package com.ask0n.cloudvault.controllers;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.Role;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.UserRole;
import com.ask0n.cloudvault.repositories.UserRepository;
import com.ask0n.cloudvault.security.JwtProvider;
import com.ask0n.cloudvault.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class AuthControllerTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private UserRepository userRepository;

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
        token = jwtProvider.generateToken(user);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.checkToken(any(String.class))).thenReturn(true);
    }

    @Test
    public void loginTest200() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\": \"Ask0n\", \"password\": \"TEST\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest400() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\": \"Ask0n\", \"password\": \"TE\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"message\": \"Bad credentials\", \"id\": 400002 }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(post("/logout")
                        .header("auth-token", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
