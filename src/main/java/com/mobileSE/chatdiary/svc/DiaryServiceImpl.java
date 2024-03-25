package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.dao.DiaryDao;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.svc.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "diary")
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryDao diaryDao;

    @Override
    public DiaryEntity createDiary(DiaryEntity diary) {
        return diaryDao.save(diary);
    }

    public List<DiaryEntity> getAllDiariesByAuthorId(Long authorId) {
        return diaryDao.findByAuthorId(authorId);
    }

}
