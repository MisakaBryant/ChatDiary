package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.dao.GPTApiDao;
import com.mobileSE.chatdiary.pojo.vo.gpt.GPTRequest;
import com.mobileSE.chatdiary.svc.GPTApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/gen/")
@RequiredArgsConstructor
public class DiaryGenCotroller {
    private final GPTApiService gptApiService;

    @GetMapping("diary")
    public CommonResponse<?> getByStringUsingChatGPt(@RequestBody GPTRequest in) {
        //StpUtil.checkLogin();
        String out = gptApiService.getByStringUsingChatGPT(in.getInput());
        return CommonResponse.success(out);
    }
}
