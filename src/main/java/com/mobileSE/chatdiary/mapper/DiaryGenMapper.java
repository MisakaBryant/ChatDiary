package com.mobileSE.chatdiary.mapper;

import com.mobileSE.chatdiary.pojo.entity.DiaryGenEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryGenVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiaryGenMapper {
    DiaryGenMapper INSTANCE = Mappers.getMapper(DiaryGenMapper.class);
    DiaryGenVO toDiaryGenVO(DiaryGenEntity diaryGenEntity);
}