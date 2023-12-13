package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.UserImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageDao extends JpaRepository<UserImageEntity, Long> {
    List<UserImageEntity> findByUserId(Long UserId);
}
