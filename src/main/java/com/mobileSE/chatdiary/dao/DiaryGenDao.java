package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.DiaryGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryGenDao extends JpaRepository<DiaryGenEntity, Long> {
}
