package com.mobileSE.chatdiary.dao;

import com.mobileSE.chatdiary.pojo.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatDao  extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByAuthorId(Long authorId);

}
