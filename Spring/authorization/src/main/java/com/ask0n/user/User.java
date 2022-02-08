package com.ask0n.user;

import com.ask0n.authorization.Authorities;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Validated
public class User {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private List<Authorities> authorities;

    public User() {}
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.authorities = new ArrayList<>();
    }
    public User(String username, String password, List<Authorities> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Authorities> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
