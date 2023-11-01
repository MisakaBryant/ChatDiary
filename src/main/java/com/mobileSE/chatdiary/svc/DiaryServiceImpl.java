package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.dao.DiaryDao;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryDao diaryDao;
@Override
    public DiaryEntity createDiary(DiaryEntity diary) {
        return diaryDao.save(diary);
    }
    @Override

    // 通过ID获取日记
    public DiaryEntity getDiaryById(Long id) {
        return diaryDao.findById(id).orElse(null);
    }
    @Override

    public DiaryEntity updateDiary(Long id, DiaryEntity updatedDiary) {
        DiaryEntity existingDiary = diaryDao.findById(id).orElse(null);
        if (existingDiary != null) {
            existingDiary.setTitle(updatedDiary.getTitle());
            existingDiary.setContent(updatedDiary.getContent());
            return diaryDao.save(existingDiary);
        }
        return null;
    }

    public void deleteDiary(Long id) {
        diaryDao.deleteById(id);
    }

    public List<DiaryEntity> getAllDiaries() {
        return diaryDao.findAll();
    }

    public List<DiaryEntity> getAllDiariesByAuthorId(Long authorId) {
        return diaryDao.findByAuthorId(authorId);
    }

}
