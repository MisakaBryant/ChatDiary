package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.config.AccessLimit;
import com.mobileSE.chatdiary.dao.DiaryImageDao;
import com.mobileSE.chatdiary.mapper.DiaryMapper;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.CreateDiaryRequest;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryVO;
import com.mobileSE.chatdiary.svc.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryImageDao diaryImageDao;

    @PostMapping("diary")
    @AccessLimit(seconds = 1, count = 10)
    public CommonResponse<?> createDiary(@RequestBody CreateDiaryRequest createDiaryRequest) {
        StpUtil.checkLogin();
        // 使用 diaryService 创建日记
        DiaryEntity diary = DiaryEntity.builder().authorId((Long.valueOf("" + StpUtil.getLoginId()))).position(createDiaryRequest.getPosition()).type(createDiaryRequest.getType()).content(createDiaryRequest.getContent()).timestamp(createDiaryRequest.getTimestamp()).title(createDiaryRequest.getTitle()).build();

        DiaryEntity diaryEntity = diaryService.createDiary(diary);
        log.info(diaryEntity.toString());
        // 将创建的日记返回
        DiaryVO diaryVO = DiaryMapper.INSTANCE.toDiaryVO(diaryEntity);
        diaryVO.setImages(diaryImageDao.findByDiaryId(diaryEntity.getId()).stream().map(DiaryImageEntity::getUrl).collect(Collectors.toList()));
        return CommonResponse.success(diaryVO);
    }

    @GetMapping("diaries")
    public CommonResponse<?> getAllDiaries() {
        StpUtil.checkLogin();
        List<DiaryVO> diaries = diaryService.getAllDiariesByAuthorId(Long.valueOf("" + StpUtil.getLoginId())).stream().map(DiaryMapper.INSTANCE::toDiaryVO).collect(Collectors.toList());

        diaries.forEach(x -> x.setImages(diaryImageDao.findByDiaryId(x.getId()).stream().map(DiaryImageEntity::getUrl).collect(Collectors.toList())));


        return CommonResponse.success(diaries);
    }
}
