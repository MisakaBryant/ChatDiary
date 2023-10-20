package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.mapper.DiaryMapper;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.vo.diary.CreateDiaryRequest;
import com.mobileSE.chatdiary.pojo.vo.diary.DiaryVO;
import com.mobileSE.chatdiary.svc.DiaryService;
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

    @PostMapping("diary")
    public CommonResponse<?> createDiary(@RequestBody CreateDiaryRequest createDiaryRequest) {
        StpUtil.checkLogin();
        // 使用 diaryService 创建日记
        DiaryEntity diary = DiaryEntity.builder().authorId((Long.valueOf("" + StpUtil.getLoginId())))
                .position(createDiaryRequest.getPosition())
                .type(createDiaryRequest.getType())
                .content(createDiaryRequest.getContent())
                .timestamp(createDiaryRequest.getTimestamp())
                .title(createDiaryRequest.getTitle()).build();

        DiaryEntity diaryEntity = diaryService.createDiary(diary);
        log.info(diaryEntity.toString());
        // 将创建的日记返回
        DiaryVO diaryVO = DiaryMapper.INSTANCE.toDiaryVO(diaryEntity);
        return CommonResponse.success(diaryVO);
    }

    @GetMapping("diaries")
    public CommonResponse<?> getAllDiaries() {
        StpUtil.checkLogin();
        List<DiaryVO> diaries = diaryService.getAllDiariesByAuthorId(Long.valueOf("" + StpUtil.getLoginId())).stream().map(DiaryMapper.INSTANCE::toDiaryVO).collect(Collectors.toList());
        log.info(diaries.toString());
        return CommonResponse.success(diaries);
    }
}
