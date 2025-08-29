package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.tourScreen.TourScreenEnum;
import com.lcsz.abcde.models.TourScreen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TourScreenRepository extends JpaRepository<TourScreen, Long> {
    Optional<TourScreen> findByUserIdAndScreen(UUID userId, TourScreenEnum screen);
}
