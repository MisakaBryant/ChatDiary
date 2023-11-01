package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.entity.MessageEntity;
import com.mobileSE.chatdiary.pojo.vo.chat.ChatRequest;

import java.util.List;

public interface ChatService {
    public List<MessageEntity> getAllChats(Long authorId);

    public MessageEntity addChats( Long authorId ,ChatRequest chatRequest);
}
