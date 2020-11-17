//package com.github.avinashkris9.movietracker.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@EnableWebSecurity
//public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
//
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//    auth.inMemoryAuthentication().withUser("avinashkris9")
//        .password(passwordEncoder().encode("movietracker"))
//        .authorities("ROLE_ADMIN");
//
//
//  }
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//   http.authorizeRequests()
//       .antMatchers(HttpMethod.POST).hasAnyRole("ROLE_ADMIN")
//       .antMatchers(HttpMethod.DELETE).hasAnyRole("ROLE_ADMIN")
//       .antMatchers(HttpMethod.PUT).hasAnyRole("ROLE_ADMIN")
//       .and().httpBasic();
//
//  }
//}
