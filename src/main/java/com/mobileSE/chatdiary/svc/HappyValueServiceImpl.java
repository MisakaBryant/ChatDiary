package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.dao.DiaryGenDao;
import com.mobileSE.chatdiary.dao.HappyValueDao;
import com.mobileSE.chatdiary.mapper.HappyValueMapper;
import com.mobileSE.chatdiary.pojo.entity.DiaryGenEntity;
import com.mobileSE.chatdiary.pojo.entity.HappyValueEntity;
import com.mobileSE.chatdiary.pojo.vo.table.HappyValueVO;
import com.mobileSE.chatdiary.svc.service.HappyValueService;
import com.mobileSE.chatdiary.svc.service.LtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class HappyValueServiceImpl implements HappyValueService {
    private final HappyValueDao happyValueDao;
    private final DiaryGenDao diaryGenDao;
    private final LtpService ltpService;

    //传入时间是今天的时间， 应该得到今天之前的时间
    private void genLossHappyValueAndReturnAll(LocalDate localDate, Long authorId) {
        //获得所有的日记
        LocalDate LastDay = localDate.minusDays(1);
        List<HappyValueEntity> happyValueList = happyValueDao.findAllByAuthorId(authorId);
        Set<LocalDate> haveDay = new HashSet<>();
        happyValueList.forEach(happyValueEntity -> {
            LocalDate startDate = happyValueEntity.getStartDate();
            if (startDate.equals(LastDay)) {
                //只要有前一天的，说明已经生成了直接return
                return;
            }
            haveDay.add(startDate);
        });

        List<DiaryGenEntity> byAuthorId = diaryGenDao.findByAuthorId(authorId);
        Set<LocalDate> neadDay = new HashSet<>();
        byAuthorId.forEach(diaryGenEntity -> {
            neadDay.add(diaryGenEntity.getDate());
        });
        Set<LocalDate> difference = new HashSet<>(neadDay);
        difference.removeAll(haveDay);
        for (LocalDate date : difference) {
            List<DiaryGenEntity> filterDiary = byAuthorId.stream().filter(diaryGenEntity -> diaryGenEntity.getDate().equals(date)).toList();
            if (filterDiary.size() != 1) {
                throw new BizException(BizError.DIARYGEN_ERROR);
            }
            DiaryGenEntity diaryGenEntity = filterDiary.get(0);
            Integer score = ltpService.callLtpApi(diaryGenEntity.getContent());
            happyValueDao.saveAndFlush(HappyValueEntity.builder().value(score).startDate(date).authorId(authorId).build());

        }
    }

    @Override
    //传入时间是今天的时间， 应该得到今天之前的时间
    public List<HappyValueVO> getHappyValuesList(Long timestamp, Long authorId) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.of("Asia/Shanghai"));
        genLossHappyValueAndReturnAll(localDate, authorId);
        //从大到校
        return happyValueDao.findAllByAuthorId(authorId).stream().map(HappyValueMapper.INSTANCE::toHappyValueVO).sorted((o1, o2) -> -o1.getStartDate().compareTo(o2.getStartDate())).toList();
    }
}
