package com.trenzadostm.trenzadostmusuarios.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmusuarios.dto.LoginRequest;
import com.trenzadostm.trenzadostmusuarios.security.TokenGenerator;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenGenerator tokenGenerator;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        String token = tokenGenerator.createToken(request.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
