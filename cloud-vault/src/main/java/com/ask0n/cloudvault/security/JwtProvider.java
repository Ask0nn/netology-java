package com.ask0n.cloudvault.security;

import com.ask0n.cloudvault.entities.Role;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Value( "${jwt.secret}" )
    private String SECRET;

    public String generateToken(User user) {
        List<String> grantedAuthorities = user.getUserRoles().stream()
                .map(UserRole::getRole)
                .map(Role::getRole)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setId("Ask0n")
                .setSubject(user.getUsername())
                .claim("authorities", grantedAuthorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }
}
