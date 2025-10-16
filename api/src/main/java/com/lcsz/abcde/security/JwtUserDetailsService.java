package com.lcsz.abcde.security;

import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.services.ClientService;
import com.lcsz.abcde.services.ClientUserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        String cnpjComputex = "12302493000101";
        if (numericLogin.length() == 14) {
            Client client = clientService.getByCnpj(login);
            if (client == null) throw new UsernameNotFoundException("Credênciais inválidas, certifique-se de possuir uma conta registrada com o cnpj e senha informados");
            if(client.getStatus().equals(ClientStatus.INACTIVE)) throw new UsernameNotFoundException("Conta desativada, entre em contato com o suporte");
//            if (client.getPassword() == null) throw new UsernameNotFoundException("A senha da conta informada ainda não foi definida, consulte o e-mail informado ou entre em contato com o suporte");
            String role = numericLogin.equals(cnpjComputex) ? "COMPUTEX" : "CLIENT";
            return new JwtUserDetails(client, role);
        }

        // Senão, assume que é um e-mail
        ClientUser clientUser = clientUserService.getByEmail(login);
        if (clientUser == null) throw new UsernameNotFoundException("Credênciais inválidas, certifique-se de possuir uma conta registrada com o e-mail e senha informados");
        if(clientUser.getStatus().equals(ClientUserStatus.INACTIVE)) throw new UsernameNotFoundException("Conta desativada, entre em contato com o suporte");
//        if (clientUser.getPassword() == null) throw new UsernameNotFoundException("A senha da conta informada ainda não foi definida, consulte o e-mail informado ou entre em contato com o suporte");

        // Checa se é um usuário da COMPUTEX
        UUID userClientId = clientUser.getClientId();
        Client client = clientService.getClientById(userClientId);
        if (client == null) throw new UsernameNotFoundException("Cliente do usuário não encontrado com CNPJ: " + login);
        if(client.getStatus().equals(ClientStatus.INACTIVE)) throw new UsernameNotFoundException("Conta desativada, entre em contato com o suporte");
//        if (client.getPassword() == null) throw new UsernameNotFoundException("A senha da conta informada ainda não foi definida, consulte o e-mail informado ou entre em contato com o suporte");
        String role = client.getCnpj().equals(cnpjComputex) ? "COMPUTEX" : "CLIENT_USER";
        return new JwtUserDetails(clientUser, role);
    }

    public JwtToken getTokenAuthenticated(String login) {
        UserDetails userDetails = loadUserByUsername(login);
        UUID id;
        UUID idClient;
        String role;

        if (userDetails instanceof JwtUserDetails jwtUserDetails) {
            id = jwtUserDetails.getId();
            role = jwtUserDetails.getRole();

            if (jwtUserDetails.isClient()) {
                // Cliente: usa o próprio ID como idClient
                idClient = jwtUserDetails.getClient().getId();
            } else if (jwtUserDetails.isClientUser()) {
                // Usuário do cliente: usa o ID do cliente vinculado
                idClient = jwtUserDetails.getClientUser().getClientId();
            } else {
                throw new RuntimeException("Erro ao identificar tipo de usuário.");
            }
        } else {
            throw new RuntimeException("Erro ao gerar token: tipo de usuário inválido.");
        }

        return JwtUtils.createToken(id, idClient, login, role, Duration.ofDays(7));
    }
}
