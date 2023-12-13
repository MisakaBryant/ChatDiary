package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.HappyValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HappyValueDao extends JpaRepository<HappyValueEntity, Long> {
}
