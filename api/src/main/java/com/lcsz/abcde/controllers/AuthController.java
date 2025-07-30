package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.auth.AuthLoginDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.exceptions.ExceptionMessage;
import com.lcsz.abcde.security.JwtToken;
import com.lcsz.abcde.security.JwtUserDetails;
import com.lcsz.abcde.security.JwtUserDetailsService;
import com.lcsz.abcde.services.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Auth Controller", description = "Contém todas as operações relacionadas aos recursos de autenticação do ABCDE")
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;
    private final AuditLogService auditLogService;
    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager, AuditLogService auditLogService) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
        this.auditLogService = auditLogService;
    }

    @Operation(
            summary = "Autenticar credenciais",
            description = "Recurso para se autenticar no sistema e acessar as demais rotas privadas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticação realizada com sucesso!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtToken.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Credenciais inválidas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    )
            }
    )
    @PostMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> auth(@RequestBody @Valid AuthLoginDto dto, HttpServletRequest request) {
        log.info("Processo de authenticação pelo login {}", dto.getLogin());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Obtem o ID do usuário autenticado
            JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
            UUID userId = userDetails.getId();

            JwtToken token = detailsService.getTokenAuthenticated(dto.getLogin());

            // Log
            String details = "Login efetuado com sucesso";
            AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.LOGIN, userId, AuditProgram.AUTH, details);
            this.auditLogService.create(logDto);

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.error("Bad Credentials from login {}", dto.getLogin());
            return ResponseEntity
                    .badRequest()
                    .body(new ExceptionMessage(request, HttpStatus.BAD_REQUEST, "Credênciais inválidas, certifique-se de possuir uma conta registrada com o CNPJ/Email e senha informada"));
        }
    }
}
