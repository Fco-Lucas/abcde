package com.lcsz.abcde.repositorys.projection;

import java.util.UUID;

public interface LotProjection {
    Long getId();
    UUID getUserId();
    String getUserCnpj();
    String getName();
    String getStatus();
}
