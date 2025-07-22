package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.models.Lot;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {
    @Query("""
        SELECT l FROM Lot l WHERE l.name = :name AND l.userCnpj = :cnpj AND l.status != 'DELETED'
    """)
    Optional<Lot> findByNameAndUserId(String name, String cnpj);

    @Query("""
        SELECT l FROM Lot l
        WHERE (:name IS NULL OR l.name ILIKE :name)
          AND (:status IS NULL OR l.status = :status)
          AND l.status != 'DELETED'
        ORDER BY l.id DESC
    """)
    Page<LotProjection> findAllPageable(
        Pageable pageable,
        @Param("name") String name,
        @Param("status") LotStatus status
    );
}
