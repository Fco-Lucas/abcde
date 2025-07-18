package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.repositorys.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByCnpjAndStatus(String cnpj, ClientStatus status);

    @Query("""
        SELECT c FROM Client c
        WHERE (:cnpj IS NULL OR c.cnpj ILIKE :cnpj)
          AND (:status IS NULL OR c.status = :status)
        ORDER BY c.createdAt DESC
    """)
    Page<ClientProjection> findAllPageable(
        Pageable pageable,
        @Param("cnpj") String cnpj,
        @Param("status") ClientStatus status
    );
}
