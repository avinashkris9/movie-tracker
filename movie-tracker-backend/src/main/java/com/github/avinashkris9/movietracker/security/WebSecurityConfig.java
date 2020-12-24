//package com.github.avinashkris9.movietracker.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//
//
//
//public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    // @formatter:off
//    http
//        .authorizeRequests(a -> a
//            .antMatchers("/", "/error", "/webjars/**").permitAll()
//            .anyRequest().authenticated()
//        )
//        .exceptionHandling(e -> e
//            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//        )
//        .oauth2Login();
//
//  }
//}
