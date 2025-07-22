package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.LotImageQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LotImageQuestionRepository extends JpaRepository<LotImageQuestion, Long> {
    Optional<LotImageQuestion> findByImageIdAndNumber(Long imageId, Integer number);

    List<LotImageQuestion> findAllByImageId(Long imageId);
}
