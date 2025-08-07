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
    @Query(value = """
        SELECT 
            id, 
            lot_id, 
            key, 
            matricula, 
            nome_aluno, 
            presenca, 
            have_modification, 
            status, 
            created_at 
        FROM lots_images
        WHERE lot_id = :lotId
          AND (:student IS NULL OR CAST(matricula AS TEXT) ILIKE :student OR nome_aluno ILIKE :student)
          AND status = 'ACTIVE'
        ORDER BY id DESC 
    """, nativeQuery = true)
    Page<LotImageProjection> findAllPageable(
            Pageable pageable,
            @Param("lotId") Long lotId,
            @Param("student") String student
    );

    List<LotImage> findAllByLotIdAndStatus(Long lotId, LotImageStatus status);

    List<LotImage> findAllByKeyAndLotIdAndStatus(String key, Long lotId, LotImageStatus status);
}
