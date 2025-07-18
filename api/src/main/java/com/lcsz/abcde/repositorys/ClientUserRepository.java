package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.repositorys.projection.ClientUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientUserRepository extends JpaRepository<ClientUser, UUID> {
    Optional<ClientUser> findByEmailAndStatus(String email, ClientUserStatus status);

    @Query("""
        SELECT u FROM ClientUser u
        WHERE u.clientId = :clientId
          AND (:name IS NULL OR u.name ILIKE :name)
          AND (:email IS NULL OR u.email ILIKE :email)
          AND (:status IS NULL OR u.status = :status)
        ORDER BY u.createdAt DESC
    """)
    Page<ClientUserProjection> findAllPageable(
        Pageable pageable,
        @Param("clientId") UUID clientId,
        @Param("name") String name,
        @Param("email") String email,
        @Param("status") ClientUserStatus status
    );

    List<ClientUser> findAllByClientIdAndStatus(UUID clientId, ClientUserStatus status);
}
