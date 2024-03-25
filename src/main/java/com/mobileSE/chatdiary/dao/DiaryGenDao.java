package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.DiaryGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryGenDao extends JpaRepository<DiaryGenEntity, Long> {
    List<DiaryGenEntity> findByAuthorId(Long authorId);

    Optional<DiaryGenEntity> findByDateAndAuthorId(LocalDate date,Long authorId);
}
