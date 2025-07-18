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

    public JwtUserDetails(Client client, String role) {
        super(
                client.getCnpj(),
                client.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
        this.client = client;
        this.clientUser = null;
    }

    public JwtUserDetails(ClientUser clientUser) {
        super(
                clientUser.getEmail(),
                clientUser.getPassword(),
                List.of(new SimpleGrantedAuthority("CLIENT_USER"))
        );
        this.client = null;
        this.clientUser = clientUser;
    }

    public UUID getId() {
        return client != null ? client.getId() : clientUser.getId();
    }

    public String getRole() {
        if(client != null)
            return client.getCnpj().equals("12302493000101") ? "COMPUTEX" : "CLIENT";

        return "CLIENT_USER";
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
