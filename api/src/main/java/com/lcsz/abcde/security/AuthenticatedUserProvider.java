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

    // Retorna o usuário autenticado
    public JwtUserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof JwtUserDetails)) {
            return null;
        }

        return (JwtUserDetails) authentication.getPrincipal();
    }

    // Retorna o ID do usuário autenticado
    public UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    // Retorna o cargo do usuário autenticado
    public String getAuthenticatedUserRole() {
        JwtUserDetails authUser = this.getAuthenticatedUser();
        return authUser == null ? null : getAuthenticatedUser().getRole();
    }

    public String getAuthenticatedUserRole(UUID userId) {
        final String COMPUTEX_CNPJ = "12302493000101";

        ClientUser clientUser = this.clientUserService.getByIdOrNull(userId);
        if(clientUser != null) {
            Client client = this.clientService.getClientById(clientUser.getClientId());
            return COMPUTEX_CNPJ.equals(client.getCnpj()) ? "COMPUTEX" : "CLIENT_USER";
        }

        Client client = this.clientService.getByIdOrNull(userId);
        if(client == null) throw new RuntimeException("Ao tentar buscar o cargo do usuário que criou o lote, com base no seu ID não foi encontrado nem cliente e nem usuário do cliente");
        return COMPUTEX_CNPJ.equals(client.getCnpj()) ? "COMPUTEX" : "CLIENT";
    }

    // Retorna se o usuário autenticado é um cliente
    public Boolean authenticatedUserIsClient() {
        return this.getAuthenticatedUser().isClient();
    }

    // Retorna se o usuário autenticado é um usuário do cliente
    public Boolean authenticatedUserIsClientUser() {
        return this.getAuthenticatedUser().isClientUser();
    }

    // Retorna o nome do usuário autenticado
//    public String getAuthenticatedUserName(UUID userId) {
//        Client client = this.clientService.getByIdOrNull(userId);
//
//        if(client == null) {
//            ClientUser clientUser = this.clientUserService.getByIdOrNull(userId);
//            if(clientUser == null) throw new RuntimeException(String.format("Usuário com id: '%s' não é nem um cliente e nem um usuário do cliente", userId));
//
//            return clientUser.getName();
//        }
//
//        return client.getName();
//    }

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

    // Retorna o client com base no id do usuário
    public Client getClientAuthenticatedUser(UUID userId) {
        return this.getClientForUser(userId);
    }

    // Retorna o cliente do usuário autenticado
    public Client getClientAuthenticatedUser() {
        // Obtem o ID do usuário autenticado
        UUID userId = this.getAuthenticatedUserId();
        return this.getClientForUser(userId);
    }

    private Client getClientForUser(UUID userId) {
        Client client = this.clientService.getByIdOrNull(userId);

        if(client == null) {
            // Se não for um cliente, busca o cliente com base no usuário autenticado e retorna-o
            ClientUser clientUser = this.clientUserService.getByIdOrNull(userId);
            return this.clientService.getClientById(clientUser.getClientId());
        }

        // Retorna o cliente caso o usuário autenticado já seja um cliente
        return client;
    }
}
