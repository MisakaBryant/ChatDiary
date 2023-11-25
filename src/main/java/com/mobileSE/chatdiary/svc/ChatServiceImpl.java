package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.dao.ChatDao;
import com.mobileSE.chatdiary.pojo.entity.MessageEntity;
import com.mobileSE.chatdiary.pojo.vo.chat.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatDao chatDao;
    private final ApiService apiService;

    @Override
    public List<MessageEntity> getAllChats(Long authorId) {
        return chatDao.findByAuthorId(authorId);
    }

    @Override
    public MessageEntity addChats(Long authorId, ChatRequest chatRequest) {
        MessageEntity newMessage = new MessageEntity();
        newMessage.setAuthorId(authorId);
        newMessage.setContent(chatRequest.getContent());
        newMessage.setIsUserMe(true);
        newMessage.setTimestamp(chatRequest.getTimestamp());
        chatDao.save(newMessage);
        log.info(newMessage.toString());
        String gptOutPut = apiService.simpleQuestion(chatRequest.getContent());
        MessageEntity gptOutMessageEntity = MessageEntity.builder().content(gptOutPut).timestamp(new Date()).isUserMe(false).authorId(authorId).build();
        log.info(gptOutMessageEntity.toString());
        return chatDao.save(gptOutMessageEntity);
    }
}
