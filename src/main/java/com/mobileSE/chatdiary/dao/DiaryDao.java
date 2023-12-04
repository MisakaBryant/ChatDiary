package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DiaryDao extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findByAuthorId(Long authorId);

}
