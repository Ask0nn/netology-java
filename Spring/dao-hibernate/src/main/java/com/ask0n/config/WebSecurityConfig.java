package com.ask0n.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ask0n").password("{noop}1234").roles("ALL");
        auth.inMemoryAuthentication().withUser("ask0n1").password("{noop}1234").roles("READ");
        auth.inMemoryAuthentication().withUser("ask0n2").password("{noop}1234").roles("WRITE");
        auth.inMemoryAuthentication().withUser("ask0n3").password("{noop}1234").roles("DELETE");
    }
}
