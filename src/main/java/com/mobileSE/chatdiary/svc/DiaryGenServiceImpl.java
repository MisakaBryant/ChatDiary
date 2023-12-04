package com.mobileSE.chatdiary.svc;

import cn.hutool.log.Log;
import com.mobileSE.chatdiary.dao.DiaryGenDao;
import com.mobileSE.chatdiary.dao.DiaryDao;
import com.mobileSE.chatdiary.dao.DiaryImageDao;
import com.mobileSE.chatdiary.mapper.DiaryGenMapper;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.entity.DiaryGenEntity;
import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryGenVO;
import com.mobileSE.chatdiary.svc.service.BaiduAipService;
import com.mobileSE.chatdiary.svc.service.DiaryGenService;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryGenServiceImpl implements DiaryGenService {
    private final DiaryDao diaryDao;
    private final DiaryGenDao diaryGenDao;
    private final DiaryImageDao diaryImageDao;
    private final BaiduAipService baiduAipService;
    private final GPTApiService gptApiService;

    @Override
    public DiaryGenEntity genDiary(Long userId, LocalDate date) {
        List<DiaryEntity> byAuthorId = diaryDao.findByAuthorId(userId);
        List<DiaryEntity> matchingDiaries = byAuthorId.stream().filter(diary -> {
            LocalDate diaryDate = LocalDate.parse(diary.getTimestamp().toString());
            return diaryDate.equals(date);
        }).toList();
        List<String> imageDescriptions = new ArrayList<String>();
        List<String> contents = new ArrayList<String>();

        matchingDiaries.forEach(diary -> {
            if (Objects.equals(diary.getType(), "IMAGE")) {
                List<DiaryImageEntity> byDiaryId = diaryImageDao.findByDiaryId(diary.getId());
                imageDescriptions.addAll(byDiaryId.stream().map(DiaryImageEntity::getDescription).toList());
            } else if (Objects.equals(diary.getType(), "TEXT")) {
                contents.add(diary.getContent() + "， 地点是" + diary.getPosition() + " 时间是" + diary.getTimestamp());
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append("日记里面有以下的图片：");
        imageDescriptions.forEach(it -> {
            sb.append(it).append("\n");
        });
        sb.append("日记里面发生了如下的事情： ");
        contents.forEach(it -> sb.append(it).append("\n"));
        String s = gptApiService.simpleQuestionWithSystem("我想要生成一个日记,你需要完成日记的标题和内容,格式：\n[日记的标题]\n[日记的内容]\n", sb.toString());
        String title = s.substring(0, s.indexOf('\n'));
        String content = s.substring(s.indexOf('\n') + 1);
        DiaryGenEntity save = diaryGenDao.save(DiaryGenEntity.builder().title(title).content(content).date(date).build());
        log.info(save.toString());
        return save;
    }

    private DiaryGenVO toDiaryGenVo(DiaryGenEntity entity) {
        DiaryGenVO diaryGenVO = DiaryGenMapper.INSTANCE.toDiaryGenVO(entity);
        List<DiaryEntity> byAuthorId = diaryDao.findByAuthorId(entity.getId());
        List<String> imageUrls = new ArrayList<String>();
        List<DiaryEntity> matchingDiaries = byAuthorId.stream().filter(diary -> {
            LocalDate diaryDate = LocalDate.parse(diary.getTimestamp().toString());
            return diaryDate.equals(entity.getDate());
        }).toList();
        matchingDiaries.forEach(diary -> imageUrls.addAll(diaryImageDao.findByDiaryId(diary.getId()).stream().map(DiaryImageEntity::getUrl).toList()));
        diaryGenVO.setImages(imageUrls);
        return diaryGenVO;
    }

    @Override
    public DiaryGenVO genDiaryVO(Long userId, LocalDate date) {
        Optional<DiaryGenEntity> byDate = diaryGenDao.findByDate(date);
        return byDate.map(this::toDiaryGenVo).orElseGet(() -> toDiaryGenVo(genDiary(userId, date)));
    }
}
