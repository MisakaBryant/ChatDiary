package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.config.AccessLimit;
import com.mobileSE.chatdiary.svc.service.DiaryGenService;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/gen/")
@RequiredArgsConstructor
public class DiaryGenController {
    private final GPTApiService apiService;
    private final DiaryGenService diaryGenService;

    @GetMapping("diary")
    @AccessLimit(seconds = 1, count = 10)
    public Mono<CommonResponse<String>> getByStringUsingChatGPT(@RequestParam String input) {
        StpUtil.checkLogin();
        return apiService.getByStringUsingChatGPT(input).map(CommonResponse::success);
    }


    /*
        得到所有填写日记的日期
     */
    @GetMapping("diaryDateList")
    @AccessLimit(seconds = 1, count = 10)

    public CommonResponse<?> getDiaryDateListGenList(@RequestParam("number") Long number) {
        StpUtil.checkLogin();
        return CommonResponse.success(diaryGenService.getDiaryDateDetail(StpUtil.getLoginIdAsLong(), number.intValue()));
    }

}
