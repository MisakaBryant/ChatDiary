package com.mobileSE.chatdiary.mapper;

import com.mobileSE.chatdiary.pojo.entity.HappyValueEntity;
import com.mobileSE.chatdiary.pojo.vo.table.HappyValueVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;


@Mapper
public interface HappyValueMapper {
    HappyValueMapper INSTANCE = Mappers.getMapper(HappyValueMapper.class);

    @Mappings({@Mapping(target = "startDate", expression = "java(localDateToString(diaryEntity.getStartDate()))")})
    HappyValueVO toHappyValueVO(HappyValueEntity diaryEntity);
    default String localDateToString(LocalDate localDate) {
        // 在这里添加你希望的日期格式，例如：yyyy-MM-dd
        return localDate.toString();
    }
}