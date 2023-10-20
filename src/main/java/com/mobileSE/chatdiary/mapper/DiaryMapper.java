package com.mobileSE.chatdiary.mapper;

import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryVO;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiaryMapper {
    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);

    DiaryVO toDiaryVO(DiaryEntity diaryEntity);
}