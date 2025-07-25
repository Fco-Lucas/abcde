package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;
import com.lcsz.abcde.models.LotImage;
import com.lcsz.abcde.repositorys.projection.LotImageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LotImageRepository extends JpaRepository<LotImage, Long> {
    @Query("""
        SELECT l FROM LotImage l 
        WHERE l.lotId = :lotId
          AND (
            :student IS NULL OR 
            CAST(l.matricula AS string) ILIKE :student OR 
            l.nomeAluno ILIKE :student
          )
          AND l.status = 'ACTIVE'
        ORDER BY l.id DESC
    """)
    Page<LotImageProjection> findAllPageable(
            Pageable pageable,
            @Param("lotId") Long lotId,
            @Param("student") String student
    );

    List<LotImage> findAllByLotIdAndStatus(Long lotId, LotImageStatus status);
}
