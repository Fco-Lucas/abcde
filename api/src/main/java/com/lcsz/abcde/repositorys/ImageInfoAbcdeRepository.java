package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.ImageInfoAbcde;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageInfoAbcdeRepository extends JpaRepository<ImageInfoAbcde, Long> {
    Optional<ImageInfoAbcde> findByLotImageId(Long lotImageId);
}
