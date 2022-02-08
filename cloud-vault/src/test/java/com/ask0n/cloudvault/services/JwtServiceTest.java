package com.ask0n.cloudvault.services;

import com.ask0n.cloudvault.TestConfig;
import com.ask0n.cloudvault.entities.JwtTokens;
import com.ask0n.cloudvault.entities.Role;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.UserRole;
import com.ask0n.cloudvault.repositories.JwtRepository;
import com.ask0n.cloudvault.security.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
public class JwtServiceTest {
    @Value("${jwt.secret}")
    private String SECRET;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private JwtRepository jwtRepository;

    @InjectMocks
    private JwtService jwtService;

    @Test
    public void checkTokenByUserIdTest() {
        final String token = "TEST TOKEN";
        final User user = User.builder().id(UUID.randomUUID()).username("Ask0n").password("TEST").build();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        when(jwtRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(jwtToken));

        final boolean checkToken = jwtService.checkToken(user);

        assertThat(checkToken).isTrue();
    }

    @Test
    public void checkTokenByNotCorrectUserIdTest() {
        final String token = "TEST TOKEN";
        final User user = User.builder().id(UUID.randomUUID()).username("Ask0n").password("TEST").build();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        when(jwtRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(jwtToken));

        user.setId(UUID.randomUUID());
        final boolean checkToken = jwtService.checkToken(user);

        assertThat(checkToken).isFalse();
    }

    @Test
    public void checkTokenByTokenTest() {
        final String token = "TEST TOKEN";
        final User user = User.builder().username("Ask0n").password("TEST").build();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        when(jwtRepository.findByToken(token)).thenReturn(Optional.of(jwtToken));

        final boolean checkToken = jwtService.checkToken(token);

        assertThat(checkToken).isTrue();
    }

    @Test
    public void checkTokenByNotCorrectTokenTest() {
        final String token = "TEST TOKEN";
        final User user = User.builder().username("Ask0n").password("TEST").build();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        when(jwtRepository.findByToken(token)).thenReturn(Optional.of(jwtToken));

        final boolean checkToken = jwtService.checkToken(token + "123");

        assertThat(checkToken).isFalse();
    }

    @Test
    public void deleteTokenByUserIdTest() {
        final String token = "TEST TOKEN";
        final User user = User.builder().id(UUID.randomUUID()).username("Ask0n").password("TEST").build();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        when(jwtRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(jwtToken));

        jwtService.deleteToken(user);

        when(jwtRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());

        final boolean checkToken = jwtService.checkToken(user);

        assertThat(checkToken).isFalse();
    }

    @Test
    public void deleteTokenByTokenTest() {
        final String token = "TEST TOKEN";
        final User user = User.builder().username("Ask0n").password("TEST").build();
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token(token).build();

        when(jwtRepository.findByToken(token)).thenReturn(Optional.of(jwtToken));

        jwtService.deleteToken(token);

        when(jwtRepository.findByToken(token)).thenReturn(Optional.empty());

        final boolean checkToken = jwtService.checkToken(token);

        assertThat(checkToken).isFalse();
    }

    @Test
    public void generateTokenByUserTest() {
        final User user = User.builder().id(UUID.randomUUID()).username("Ask0n").password("TEST").build();
        final Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.builder().user(user).role(Role.builder().role("ROLE_USER").build()).build());
        user.setUserRoles(userRoles);
        final JwtTokens jwtToken = JwtTokens.builder().user(user).token("TEST TOKEN").build();
        final String token = Jwts.builder()
                .setId("Ask0n")
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();


        when(jwtRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(jwtToken));
        when(jwtRepository.save(any(JwtTokens.class))).thenReturn(jwtToken);
        when(jwtProvider.generateToken(user)).thenReturn(token);

        final String generatedToken = jwtService.generateToken(user);
        final String usernameFromToken = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(generatedToken)
                .getBody()
                .getSubject();

        assertThat(usernameFromToken).isEqualTo(user.getUsername());
    }
}
