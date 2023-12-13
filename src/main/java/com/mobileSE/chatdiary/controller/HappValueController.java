package com.mobileSE.chatdiary.controller;

import com.mobileSE.chatdiary.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/happydata/")
@RequiredArgsConstructor
public class HappValueController {


    @GetMapping("list")
    public CommonResponse<?> getHappyValueListOfTimestamp(@RequestParam("timestamp") Long timestamp) {

    }


}
