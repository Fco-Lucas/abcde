package com.lcsz.abcde.security;

import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.services.ClientService;
import com.lcsz.abcde.services.ClientUserService;
import com.lcsz.abcde.services.PermissionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@Component
public class AuthenticatedUserProvider {
    private final PermissionService permissionService;
    private final ClientService clientService;
    private final ClientUserService clientUserService;

    AuthenticatedUserProvider(
            @Lazy PermissionService permissionService,
            @Lazy ClientService clientService,
            @Lazy ClientUserService clientUserService
    ) {
        this.permissionService = permissionService;
        this.clientService = clientService;
        this.clientUserService = clientUserService;
    }

    public JwtUserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        return (JwtUserDetails) authentication.getPrincipal();
    }

    public UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    public String getLogin() {
        return getAuthenticatedUser().getUsername();
    }

    public String getAuthenticatedUserName(UUID userId) {
        Client client = this.clientService.getByIdOrNull(userId);

        if(client == null) {
            ClientUser clientUser = this.clientUserService.getByIdOrNull(userId);
            if(clientUser == null) throw new RuntimeException(String.format("Usuário com id: '%s' não é nem um cliente e nem um usuário do cliente", userId));

            return clientUser.getName();
        }

        return client.getName();
    }

    public PermissionResponseDto getAuthenticatedUserPermissions() {
        UUID userId = this.getAuthenticatedUserId();
        return this.getPermissionForUser(userId);
    }

    public PermissionResponseDto getAuthenticatedUserPermissions(UUID userId) {
        return this.getPermissionForUser(userId);
    }

    private PermissionResponseDto getPermissionForUser(UUID userId) {
        // Verifica se o usuário é cliente ou clientUser
        Client client = this.clientService.getByIdOrNull(userId);

        if(client == null) {
            ClientUser clientUser = this.clientUserService.getByIdOrNull(userId);
            if(clientUser == null) throw new RuntimeException(String.format("Usuário com id: '%s' não é nem um cliente e nem um usuário do cliente", userId));

            // Se for usuário do cliente busca a sua permissão com base no ID e retorna
            return this.permissionService.getPermissionByIdDto(clientUser.getPermission());
        }else {
            // Se for cliente busca a permissão cujo pode fazer tudo e retorna
            return this.permissionService.getMainPermission();
        }
    }
}
