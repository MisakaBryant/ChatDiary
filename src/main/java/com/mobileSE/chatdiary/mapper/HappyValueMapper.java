package com.mobileSE.chatdiary.mapper;

import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.entity.HappyValueEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryVO;
import com.mobileSE.chatdiary.pojo.vo.table.HappyValueVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;




@Mapper
public interface HappyValueMapper {
    HappyValueMapper INSTANCE = Mappers.getMapper(HappyValueMapper.class);
    HappyValueVO toHappyValueVO(HappyValueEntity diaryEntity);
}