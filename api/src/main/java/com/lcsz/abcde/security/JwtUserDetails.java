package com.lcsz.abcde.security;

import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

public class JwtUserDetails extends User {
    private final Client client;
    private final ClientUser clientUser;
    private final String role;

    // A senha pode vir null ao cadastrar os clientes e enviar e-mails pois gera um JWT
    public JwtUserDetails(Client client, String role) {
        super(
                client.getCnpj(),
                client.getPassword() == null ? "" : client.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
        this.client = client;
        this.clientUser = null;
        this.role = role;
    }

    // A senha pode vir null ao cadastrar os usuários dos clientes e enviar e-mails pois gera um JWT
    public JwtUserDetails(ClientUser clientUser, String role) {
        super(
                clientUser.getEmail(),
                clientUser.getPassword() == null ? "" : clientUser.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
        this.client = null;
        this.clientUser = clientUser;
        this.role = role;
    }

    public UUID getId() {
        return client != null ? client.getId() : clientUser.getId();
    }

    public String getRole() {
        return role;
    }

    public boolean isClient() {
        return client != null;
    }

    public boolean isClientUser() {
        return clientUser != null;
    }

    public Client getClient() {
        return client;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }
}
