package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.LotImage;
import com.lcsz.abcde.repositorys.projection.LotImageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LotImageRepository extends JpaRepository<LotImage, Long> {
    @Query("""
        SELECT l FROM LotImage l WHERE l.lotId = :lotId ORDER BY l.id DESC
    """)
    Page<LotImageProjection> findAllPageable(
            Pageable pageable,
            @Param("lotId") Long lotId
    );
}
