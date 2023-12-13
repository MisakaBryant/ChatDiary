package com.mobileSE.chatdiary.mapper;

import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiaryMapper {
    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);
    @Mapping(target  = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
    DiaryVO toDiaryVO(DiaryEntity diaryEntity);
}