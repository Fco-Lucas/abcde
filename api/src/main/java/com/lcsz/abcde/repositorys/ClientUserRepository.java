package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.repositorys.projection.ClientUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClientUserRepository extends JpaRepository<ClientUser, UUID> {
    Optional<ClientUser> findByEmailAndStatus(String email, ClientUserStatus status);

    @Query("""
        SELECT u FROM ClientUser u
        WHERE (:name IS NULL OR u.name LIKE CONCAT('%', COALESCE(:name, ''), '%'))
          AND (:email IS NULL OR u.email LIKE CONCAT('%', COALESCE(:email, ''), '%'))
    """)
    Page<ClientUserProjection> findAllPageable(
        Pageable pageable,
        @Param("name") String name,
        @Param("email") String email
    );
}
