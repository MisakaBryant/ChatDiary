package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.DiaryGenEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryGenVO;

import java.time.LocalDate;
import java.util.List;

public interface DiaryGenService {

    DiaryGenEntity genDiary(Long userId, LocalDate date);

    DiaryGenVO genDiaryVO(Long userId, LocalDate date);

    List<DiaryGenVO> getDiaryDateDetail(Long userId, int number);

}
