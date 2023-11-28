package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryImageDao extends JpaRepository<DiaryImageEntity, Long> {
    List<DiaryImageEntity> findByDiaryId(Long authorId);

}
