package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.pojo.vo.gpt.GPTRequest;
import com.mobileSE.chatdiary.svc.service.DiaryGenService;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/gen/")
@RequiredArgsConstructor
public class DiaryGenCotroller {
    private final GPTApiService apiService;
    private final DiaryGenService diaryGenService;

    @GetMapping("diary")
    public CommonResponse<?> getByStringUsingChatGPT(@RequestBody GPTRequest in) {
        StpUtil.checkLogin();
        String out = apiService.getByStringUsingChatGPT(in.getInput());
        return CommonResponse.success(out);
    }

    /*
        得到所有填写日记的日期
     */
    @GetMapping("diaryDateList")
    public CommonResponse<?> getDiaryDateListGenList(@RequestParam("number") Long number) {
        StpUtil.checkLogin();
        return CommonResponse.success(diaryGenService.getDiaryDateDetail(StpUtil.getLoginIdAsLong(), number.intValue()));
    }

    /*
    得到所有填写日记的日期
 */
    @GetMapping("diaryDate")
    public CommonResponse<?> getDiaryDateListGenList(@RequestParam("date") String date) {
        StpUtil.checkLogin();
        return CommonResponse.success(diaryGenService.genDiaryVO(StpUtil.getLoginIdAsLong(), LocalDate.parse(date)));
    }
}
