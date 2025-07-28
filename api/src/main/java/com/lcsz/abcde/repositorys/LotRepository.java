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
import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, Long> {
    @Query("""
        SELECT l FROM Lot l WHERE l.name = :name AND l.userCnpj = :cnpj AND l.status != 'DELETED'
    """)
    Optional<Lot> findByNameAndUserId(String name, String cnpj);

    @Query("""
        SELECT l FROM Lot l
        LEFT JOIN Client c ON l.userCnpj = c.cnpj
        WHERE (:name IS NULL OR l.name ILIKE :name)
          AND (
               :client IS NULL
               OR c.name ILIKE :client
               OR c.cnpj ILIKE :client
          )
          AND (
            (:status IS NOT NULL AND l.status = :status)
            OR (:status IS NULL AND l.status != 'DELETED')
          )
        ORDER BY l.id DESC
    """)
    Page<LotProjection> findAllPageableComputex(
            Pageable pageable,
            @Param("name") String name,
            @Param("client") String client,
            @Param("status") LotStatus status
    );

    @Query("""
        SELECT l FROM Lot l
        LEFT JOIN Client c ON l.userId = c.id
        LEFT JOIN ClientUser u ON l.userId = u.id
        WHERE l.userCnpj = :clientCnpj
          AND (:name IS NULL OR l.name ILIKE :name)
          AND (
               :user IS NULL
               OR u.name ILIKE :user
               OR u.email ILIKE :user
               OR c.name ILIKE :user
               OR c.cnpj ILIKE :user
          )
          AND (
            (:status IS NOT NULL AND l.status = :status)
            OR (:status IS NULL AND l.status != 'DELETED')
          )
        ORDER BY l.id DESC
    """)
    Page<LotProjection> findAllPageableClient(
            Pageable pageable,
            @Param("name") String name,
            @Param("clientCnpj") String clientCnpj,
            @Param("user") String user,
            @Param("status") LotStatus status
    );

    @Query("""
        SELECT l FROM Lot l
        WHERE l.userId = :clientUserId
          AND l.userCnpj = :clientCnpj
          AND (:name IS NULL OR l.name ILIKE :name) 
          AND (
            (:status IS NOT NULL AND l.status = :status)
            OR (:status IS NULL AND l.status != 'DELETED')
          )
        ORDER BY l.id DESC
    """)
    Page<LotProjection> findAllPageableClientUser(
            Pageable pageable,
            @Param("name") String name,
            @Param("clientCnpj") String clientCnpj,
            @Param("clientUserId") UUID clientUserId,
            @Param("status") LotStatus status
    );
}
