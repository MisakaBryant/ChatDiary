package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.dao.DiaryGenDao;
import com.mobileSE.chatdiary.dao.DiaryDao;
import com.mobileSE.chatdiary.svc.service.DiaryGenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryGenServiceImpl implements DiaryGenService {
    private final DiaryDao diaryDao;
    private final DiaryGenDao diaryGenDao;

    @Override
    public String genDiary(Date date) {
        return null;
    }
}
