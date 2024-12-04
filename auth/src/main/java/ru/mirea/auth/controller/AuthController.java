package ru.mirea.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.mirea.auth.entity.*;
import ru.mirea.auth.service.UserService;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final String secret = "JAHFIDS8724yuJDGBHFJKdsgyfgsdhjfg&^%FDTS&TFGYuk";
    private final UserService userService;
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public Long performRegistration(@RequestBody User user) {
        return userService.register(user);
    }
    @GetMapping("users")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @PostMapping("/seller")
    public Map<String, String> setSellerRole() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            user = userService.updateToSeller(user.getId());
        } catch (UsernameNotFoundException e) {
            return Map.of("error", "bad token");
        }
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(60).toInstant()))
                .sign(Algorithm.HMAC512(this.secret.getBytes()));
        return Map.of("token", token);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(LoginAlreadyDefinedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}