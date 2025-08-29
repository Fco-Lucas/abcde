package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.enums.lot.LotType;
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
        SELECT l FROM Lot l WHERE LOWER(l.name) = LOWER(:name) AND l.userCnpj = :cnpj AND l.type = :type AND l.status != 'DELETED'
    """)
    Optional<Lot> findExistsLot(
            @Param("name") String name,
            @Param("cnpj") String cnpj,
            @Param("type") LotType type
    );

    @Query(value = """
        SELECT L.id, L.user_id, L.user_cnpj, C.name AS clientName, L.name, L.type, L.status, L.created_at FROM lots L
            INNER JOIN clients C ON L.user_cnpj = C.cnpj
            LEFT JOIN clients_users U ON L.user_id = U.id
        WHERE (COALESCE(:name, '') = '' OR L.name ILIKE :name)
            AND (COALESCE(:client, '') = '' OR (C.name ILIKE :client OR C.cnpj ILIKE :client))
            AND (COALESCE(:clientUser, '') = '' OR (U.name ILIKE :clientUser OR U.email ILIKE :clientUser))
            AND (
                (CAST(:status AS varchar) IS NULL  AND L.status <> 'DELETED')
             OR (CAST(:status AS varchar) IS NOT NULL AND L.status = CAST(:status AS varchar))
          )
        ORDER BY L.id DESC
    """, nativeQuery = true)
    Page<LotProjection> findAllPageable(
            Pageable pageable,
            @Param("name") String name,
            @Param("client") String client,
            @Param("clientUser") String clientUser,
            @Param("status") String status
    );
}
