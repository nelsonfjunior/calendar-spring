package com.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.api.services.GoogleAuthService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginGoogleController {

	@Autowired
	private GoogleAuthService googleAuthService;


    @GetMapping("/login/google")
    public RedirectView googleConnectionStatus(HttpServletRequest request) {
        return new RedirectView(googleAuthService.getAuthorizationUrl());
    }

    @GetMapping(value = "/login/google", params = "code")
    public ResponseEntity<String> oauth2Callback(@RequestParam String code) {
        try {
            googleAuthService.authenticateUser(code);
            return new ResponseEntity<>("Autenticação bem-sucedida!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro na autenticação: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}