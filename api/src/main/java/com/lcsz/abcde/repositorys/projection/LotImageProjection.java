package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;

public interface LotImageProjection {
    Long getId();
    Long getLotId();
    String getKey();
    LotImageStatus getStatus();
}
