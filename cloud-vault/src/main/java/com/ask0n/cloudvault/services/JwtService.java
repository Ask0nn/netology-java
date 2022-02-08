package com.ask0n.cloudvault.services;

import com.ask0n.cloudvault.entities.JwtTokens;
import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.repositories.JwtRepository;
import com.ask0n.cloudvault.security.JwtProvider;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class JwtService {
    private final JwtRepository jwtRepository;
    private final JwtProvider jwtProvider;

    public JwtService(JwtRepository jwtRepository, JwtProvider jwtProvider) {
        this.jwtRepository = jwtRepository;
        this.jwtProvider = jwtProvider;
    }

    public boolean checkToken(User user) {
        return jwtRepository.findByUser_Id(user.getId()).isPresent();
    }

    public boolean checkToken(String token) {
        return jwtRepository.findByToken(token).isPresent();
    }

    @Transactional
    public void deleteToken(User user) {
        if (checkToken(user)) {
            jwtRepository.delete(user.getToken());
        }
    }

    @Transactional
    public void deleteToken(String token) {
        if (checkToken(token)) {
            jwtRepository.deleteByToken(token);
        }
    }

    public String generateToken(User user) {
        deleteToken(user);
        String token = jwtProvider.generateToken(user);
        jwtRepository.save(JwtTokens.builder()
                .user(user)
                .token(token)
                .build());
        return token;
    }
}
