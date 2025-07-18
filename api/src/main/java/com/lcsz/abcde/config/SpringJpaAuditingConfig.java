package com.lcsz.abcde.config;

import com.lcsz.abcde.security.JwtUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof JwtUserDetails userDetails) {
            return Optional.of(userDetails.getId());
        }

        return Optional.empty();
    }
}
