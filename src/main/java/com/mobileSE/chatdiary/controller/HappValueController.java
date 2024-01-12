package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.config.AccessLimit;
import com.mobileSE.chatdiary.pojo.vo.table.HappyValueVO;
import com.mobileSE.chatdiary.svc.service.HappyValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/happydata/")
@RequiredArgsConstructor
@Slf4j
public class HappValueController {
    private final HappyValueService happyValueService;

    @GetMapping("list")
    @AccessLimit(seconds = 1, count = 10)
    public CommonResponse<?> getHappyValueListOfTimestamp(@RequestParam("timestamp") Long timestamp) {
        StpUtil.checkLogin();
        try {
            List<HappyValueVO> happyValuesList = happyValueService.getHappyValuesList(timestamp, StpUtil.getLoginIdAsLong());
            log.info(happyValuesList.toString());
            return CommonResponse.success(happyValuesList);
        } catch (BizException e) {
            return CommonResponse.error(e);
        }
    }


}
