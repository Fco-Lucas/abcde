package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.models.Client;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {
    public static ClientResponseDto toDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDto.class);
    }

    public static List<ClientResponseDto> toListDto(List<Client> clients) {
        return clients.stream().map(client -> toDto(client)).collect(Collectors.toList());
    }
}
