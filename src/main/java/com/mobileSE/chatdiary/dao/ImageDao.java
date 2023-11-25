package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageDao extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByDiaryId(Long authorId);
}
