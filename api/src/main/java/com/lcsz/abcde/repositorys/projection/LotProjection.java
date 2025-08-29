package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.lot.LotType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LotProjection {
    Long getId();
    UUID getUserId();
    String getUserCnpj();
    String getClientName();
    String getName();
    LotType getType();
    String getStatus();
    LocalDateTime getCreatedAt();
}
