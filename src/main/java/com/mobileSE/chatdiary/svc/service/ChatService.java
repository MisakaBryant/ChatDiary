package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.MessageEntity;
import com.mobileSE.chatdiary.pojo.vo.chat.ChatRequest;

import java.util.List;

public interface ChatService {
    List<MessageEntity> getAllChats(Long authorId);

    void addChats(Long authorId, ChatRequest chatRequest);
}
