package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.vo.image.ImageUploadRequest;
import com.mobileSE.chatdiary.svc.service.DiaryService;
import com.mobileSE.chatdiary.svc.service.ImageService;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final GPTApiService apiService;
    private final DiaryService diaryService;

    private String removeQuotes(String value) {
        if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    @PostMapping("image")
    public CommonResponse<?> uploadImageDiary(@RequestParam("image") List<MultipartFile> image, @RequestParam("type") String type, @RequestParam("position") String position, @RequestParam("content") String content) throws ParseException {
        StpUtil.checkLogin();
        position = removeQuotes(position);
        type = removeQuotes(type);
        content = removeQuotes(content);
        DiaryEntity diary = DiaryEntity.builder().authorId((Long.valueOf("" + StpUtil.getLoginId()))).position(position).type(type).content(content).timestamp(new Date()).title("添加的图片").build();
        DiaryEntity diaryEntity = diaryService.createDiary(diary);
        image.forEach(x ->
                imageService.uploadDiaryImageByDate(x, new Date(), diaryEntity.getId()));

        return CommonResponse.success();
    }

}
