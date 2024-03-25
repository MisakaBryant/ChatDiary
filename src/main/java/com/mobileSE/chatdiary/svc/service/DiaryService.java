package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;

import java.util.List;

public interface DiaryService {


    DiaryEntity createDiary(DiaryEntity diary);

    // 通过ID获取日记



    List<DiaryEntity> getAllDiariesByAuthorId(Long authorId);

}
