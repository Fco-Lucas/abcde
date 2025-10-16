package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.Email;
import com.lcsz.abcde.repositorys.projection.EmailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query(value = """
            SELECT * FROM emails WHERE status_code IS NULL AND appointment_date <= NOW()
            """, nativeQuery = true)
    List<EmailProjection> findAllForSend();
}
