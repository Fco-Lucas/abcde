package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.ImageInfoVtb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageInfoVtbRepository extends JpaRepository<ImageInfoVtb, Long> {
    Optional<ImageInfoVtb> findByLotImageId(Long lotImageId);
}
