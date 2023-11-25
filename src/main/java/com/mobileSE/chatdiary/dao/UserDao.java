package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);


}