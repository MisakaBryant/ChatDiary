package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.dao.DiaryDao;
import com.mobileSE.chatdiary.dao.DiaryGenDao;
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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

        Optional<DiaryGenEntity> byDate = diaryGenDao.findByDateAndAuthorId(date, userId);
        if (byDate.isPresent()) return byDate.get();
        List<DiaryEntity> byAuthorId = diaryDao.findByAuthorId(userId);
        List<DiaryEntity> matchingDiaries = byAuthorId.stream().filter(diary -> {
            Instant instant = diary.getTimestamp().toInstant();
            LocalDate diaryDate = instant.atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
            return diaryDate.equals(date);
        }).toList();
        List<String> imageDescriptions = new ArrayList<String>();
        List<String> contents = new ArrayList<String>();

        matchingDiaries.forEach(diary -> {
            if (Objects.equals(diary.getType(), "IMAGE")) {
                List<DiaryImageEntity> byDiaryId = diaryImageDao.findByDiaryId(diary.getId());
                imageDescriptions.addAll(byDiaryId.stream().map(DiaryImageEntity::getDescription).toList());
            } else if (Objects.equals(diary.getType(), "TXT")) {
                contents.add(diary.getContent() + "， 地点是" + diary.getPosition() + " 时间是" + diary.getTimestamp());
            }
        });
        StringBuilder sb = new StringBuilder();
        if (!imageDescriptions.isEmpty()) {
            sb.append("日记里面有以下的图片：");
            imageDescriptions.forEach(it -> {
                sb.append(it).append("\n");
            });
        }
        if (!contents.isEmpty()) {
            sb.append("日记里面发生了如下的事情： ");
            contents.forEach(it -> sb.append(it).append("\n"));
        }
        String s;
        String title;
        String content;
        if (!sb.isEmpty()) {
            s = gptApiService.simpleQuestionWithSystem("我想要生成一个日记,你需要完成日记的标题和内容,我等一下要用" + "String title = s.substring(0, s.indexOf('\\n'))来获取标题， 用String content = s.substring(s.indexOf('\\n') + 1)来获取内容，所以第一行标题，后面是内容，千万不要主观联想，把内容用语言组织起来，好玩联系的内容就抛弃他 不要超过250字\n", sb.toString());
            title = s.substring(0, s.indexOf('\n'));
            content = s.substring(s.indexOf('\n') + 1);
        } else {
            title = "今天没有写日记哦";
            content = "今天没有写日记哦";
        }
        log.info("保存的时间是：" + date);
        DiaryGenEntity save = diaryGenDao.saveAndFlush(DiaryGenEntity.builder().title(title).content(content).date(date).authorId(userId).build());
        log.info(save.toString());
        return save;
    }

    private DiaryGenVO toDiaryGenVo(DiaryGenEntity entity) {
        DiaryGenVO diaryGenVO = DiaryGenMapper.INSTANCE.toDiaryGenVO(entity);
        List<DiaryEntity> byAuthorId = diaryDao.findByAuthorId(entity.getAuthorId());
        List<String> imageUrls = new ArrayList<String>();
        List<DiaryEntity> matchingDiaries = byAuthorId.stream().filter(diary -> {
            Instant instant = diary.getTimestamp().toInstant();
            LocalDate diaryDate = instant.atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
            return diaryDate.equals(entity.getDate());
        }).toList();
        matchingDiaries.forEach(diary -> imageUrls.addAll(diaryImageDao.findByDiaryId(diary.getId()).stream().map(DiaryImageEntity::getUrl).toList()));
        diaryGenVO.setImages(imageUrls);
        return diaryGenVO;
    }

    @Override
    public DiaryGenVO genDiaryVO(Long userId, LocalDate date) {
        Optional<DiaryGenEntity> byDate = diaryGenDao.findByDateAndAuthorId(date, userId);
        log.info("userId " + userId + " date " + date + " genDiaryVo");
        if (byDate.isEmpty()) {
            return toDiaryGenVo(genDiary(userId, date));
        } else {
            return toDiaryGenVo(byDate.get());
        }
    }

    @Override
    public List<DiaryGenVO> getDiaryDateDetail(Long userId, int numberOfDiary) {
        List<DiaryEntity> byAuthorId = diaryDao.findByAuthorId(userId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<DiaryGenVO> dates = new ArrayList<>();
        Set<String> dateStringSet = new HashSet<>();
        dateStringSet.add(dateFormat.format(new Date()));
        log.info("getDiaryDateDetail" + dateFormat.format(new Date()));
        int left = numberOfDiary;
        for (DiaryEntity diaryEntity : byAuthorId) {
            String format = dateFormat.format(diaryEntity.getTimestamp());
            if (!dateStringSet.contains(format)) {
                dates.add(genDiaryVO(userId, LocalDate.parse(format)));
                dateStringSet.add(format);
                left--;
                if (left == 0) {
                    break;
                }
            }
        }
        return dates;
    }


}
