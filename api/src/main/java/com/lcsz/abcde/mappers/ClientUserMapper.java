package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.models.ClientUser;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClientUserMapper {
    public static ClientUserResponseDto toDto(ClientUser clientUser) {
        return new ModelMapper().map(clientUser, ClientUserResponseDto.class);
    }

    public static List<ClientUserResponseDto> toListDto(List<ClientUser> clientUsers) {
        return clientUsers.stream().map(clientUser -> toDto(clientUser)).collect(Collectors.toList());
    }
}
