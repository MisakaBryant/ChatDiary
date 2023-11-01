package com.mobileSE.chatdiary.mapper;

import com.mobileSE.chatdiary.pojo.entity.MessageEntity;
import com.mobileSE.chatdiary.pojo.vo.chat.MessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    @Mapping(target  = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")

    MessageVO toMassageVO(MessageEntity messageEntity);
}
