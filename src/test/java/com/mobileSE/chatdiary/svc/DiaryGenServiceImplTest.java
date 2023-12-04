package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.vo.diary.DiaryGenVO;
import com.mobileSE.chatdiary.svc.service.DiaryGenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class DiaryGenServiceImplTest {
    @Autowired
    private DiaryGenService dummyGenService;


    @Test
    public void testGenDiaryVO() {
        DiaryGenVO diaryGenVO = dummyGenService.genDiaryVO(1L, LocalDate.now());
        System.out.println(diaryGenVO);
    }


}