package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;

import java.util.Date;
import java.util.List;

public interface DiaryService {


    public DiaryEntity createDiary(DiaryEntity diary);

    // 通过ID获取日记
    public DiaryEntity getDiaryById(Long id);

    public DiaryEntity updateDiary(Long id, DiaryEntity updatedDiary);

    public void deleteDiary(Long id);

    public List<DiaryEntity> getAllDiaries();

    public List<DiaryEntity> getAllDiariesByAuthorId(Long authorId);

    public DiaryEntity getDiaryByAuthorIdAndTimestamp(Long authorId, Date date);
}
