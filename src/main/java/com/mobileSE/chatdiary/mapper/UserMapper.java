package com.mobileSE.chatdiary.mapper;


import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserVO toUserVO(UserEntity userEntity);
}