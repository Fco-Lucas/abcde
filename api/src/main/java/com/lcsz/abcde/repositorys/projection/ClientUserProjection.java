package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.clientUser.ClientUserStatus;

import java.util.UUID;

public interface ClientUserProjection {
    UUID getId();
    UUID getClientId();
    String getName();
    String getEmail();
    Long getPermission();
    ClientUserStatus getStatus();
}
