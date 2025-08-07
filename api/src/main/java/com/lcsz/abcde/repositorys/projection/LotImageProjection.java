package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;

import java.time.LocalDateTime;

public interface LotImageProjection {
    Long getId();
    Long getLotId();
    String getKey();
    Integer getMatricula();
    String getNomeAluno();
    Integer getPresenca();
    Boolean getHaveModification();
    LotImageStatus getStatus();
    LocalDateTime getCreatedAt();
}
