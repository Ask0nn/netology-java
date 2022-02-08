package com.ask0n.cloudvault.controllers;

import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.models.AuthRequest;
import com.ask0n.cloudvault.services.AuthService;
import com.ask0n.cloudvault.services.JwtService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login(@RequestBody @Valid AuthRequest authRequest) {
        User user = authService.findByUsernameAndPassword(authRequest.getLogin(), authRequest.getPassword());
        String token = jwtService.generateToken(user);
        return Collections.singletonMap("auth-token", token);
    }

    @PostMapping("logout")
    public void logout(@RequestHeader("auth-token") String token) {
        jwtService.deleteToken(token.replace("Bearer ", ""));
    }
}
