package com.mobileSE.chatdiary.controller;

import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.pojo.vo.gpt.GPTRequest;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/gen/")
@RequiredArgsConstructor
public class DiaryGenCotroller {
    private final GPTApiService apiService;

    @GetMapping("diary")
    public CommonResponse<?> getByStringUsingChatGPT(@RequestBody GPTRequest in) {
        //StpUtil.checkLogin();
        String out = apiService.getByStringUsingChatGPT(in.getInput());
        return CommonResponse.success(out);
    }
}
