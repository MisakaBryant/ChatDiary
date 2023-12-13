package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.APiType;
import com.mobileSE.chatdiary.pojo.entity.GPTApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GPTApiDao extends JpaRepository<GPTApiEntity, Long> {
    List<GPTApiEntity> findAllByType(APiType type);
}
