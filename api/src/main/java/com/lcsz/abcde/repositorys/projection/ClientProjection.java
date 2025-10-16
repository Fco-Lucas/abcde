package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.client.ClientStatus;

import java.util.UUID;

public interface ClientProjection {
    UUID getId();
    String getName();
    String getEmail();
    String getCnpj();
    ClientStatus getStatus();
}
