package com.github.avinashkris9.movietracker.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class AuthController {
  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }
}
