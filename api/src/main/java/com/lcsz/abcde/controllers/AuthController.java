package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.auth.AuthLoginDto;
import com.lcsz.abcde.exceptions.ExceptionMessage;
import com.lcsz.abcde.security.JwtToken;
import com.lcsz.abcde.security.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")

public class AuthController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;
    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public ResponseEntity<?> auth(@RequestBody @Valid AuthLoginDto dto, HttpServletRequest request) {
        log.info("Processo de authenticação pelo login {}", dto.getLogin());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getLogin());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.error("Bad Credentials from login {}", dto.getLogin());
            return ResponseEntity
                    .badRequest()
                    .body(new ExceptionMessage(request, HttpStatus.BAD_REQUEST, "Credênciais inválidas, certifique-se de possuir uma conta registrada com o CNPJ/Email e senha informada"));
        }
    }
}
