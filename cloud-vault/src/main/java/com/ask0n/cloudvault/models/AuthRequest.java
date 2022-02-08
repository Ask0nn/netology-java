package com.ask0n.cloudvault.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Validated
public class AuthRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
