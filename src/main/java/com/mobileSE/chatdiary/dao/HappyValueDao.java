package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.HappyValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface HappyValueDao extends JpaRepository<HappyValueEntity, Long> {
    List<HappyValueEntity> findAllByAuthorIdAndStartDate(Long authorId, LocalDate startDate);

    List<HappyValueEntity> findAllByAuthorId(Long authorId);
}
