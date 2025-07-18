package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.models.ClientUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class ClientUserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.addMappings(new PropertyMap<ClientUser, ClientUserResponseDto>() {
            @Override
            protected void configure() {
                // Ignora o mapeamento do campo 'permission'
                skip(destination.getPermission());
            }
        });
    }

    public static ClientUserResponseDto toDto(ClientUser clientUser) {
        return modelMapper.map(clientUser, ClientUserResponseDto.class);
    }

    public static List<ClientUserResponseDto> toListDto(List<ClientUser> clientUsers) {
        return clientUsers.stream().map(ClientUserMapper::toDto).collect(Collectors.toList());
    }
}

