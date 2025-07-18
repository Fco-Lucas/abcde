package com.lcsz.abcde.security;

import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.services.ClientService;
import com.lcsz.abcde.services.ClientUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final ClientService clientService;
    private final ClientUserService clientUserService;

    public JwtUserDetailsService(ClientService clientService, ClientUserService clientUserService) {
        this.clientService = clientService;
        this.clientUserService = clientUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Checa se é CNPJ válido (14 números, com ou sem máscara)
        String numericLogin = login.replaceAll("[^\\d]", "");
        if (numericLogin.length() == 14) {
            Client client = clientService.getByCnpj(login, ClientStatus.ACTIVE);
            if (client == null) throw new UsernameNotFoundException("Client não encontrado com CNPJ: " + login);
            String role = numericLogin.equals("12302493000101") ? "COMPUTEX" : "CLIENT";
            return new JwtUserDetails(client, role);
        }

        // Senão, assume que é um e-mail
        ClientUser clientUser = clientUserService.getByEmail(login, ClientUserStatus.ACTIVE);
        if (clientUser == null) throw new UsernameNotFoundException("ClientUser não encontrado com e-mail: " + login);

        return new JwtUserDetails(clientUser);
    }

    public JwtToken getTokenAuthenticated(String login) {
        UserDetails userDetails = loadUserByUsername(login);
        UUID id;
        String role;

        if (userDetails instanceof JwtUserDetails jwtUserDetails) {
            id = jwtUserDetails.getId();
            role = jwtUserDetails.getRole();
        } else {
            throw new RuntimeException("Erro ao gerar token: tipo de usuário inválido.");
        }

        return JwtUtils.createToken(id, login, role);
    }
}
