package com.mobileSE.chatdiary.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.mapper.UserMapper;
import com.mobileSE.chatdiary.pojo.vo.user.EditUserInfoRequest;
import com.mobileSE.chatdiary.pojo.vo.user.LoginRequest;
import com.mobileSE.chatdiary.pojo.vo.user.RegisterRequest;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import com.mobileSE.chatdiary.svc.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("session")
    public CommonResponse<?> login(@Valid @RequestBody LoginRequest request) {
        // Throws BizException if auth failed.
        userService.login(request.getUsername(), request.getPassword());

        StpUtil.login(request.getUsername());
        return CommonResponse.success();
    }

    @PostMapping("user")
    public CommonResponse<?> register(@Valid @RequestBody RegisterRequest request) {
        // Throws BizException if register failed.
        userService.register(request.getUsername(), request.getPassword(), request.getPhone());

        return CommonResponse.success();
    }

    @DeleteMapping("session")
    public CommonResponse<?> logout() {
        StpUtil.checkLogin();
        StpUtil.logout();
        return CommonResponse.success(200);
    }

    @GetMapping("user")
    public CommonResponse<UserVO> userInfo() {
        StpUtil.checkLogin();
        return CommonResponse.success(UserMapper.INSTANCE.toUserVO(userService.findByUserName(String.valueOf(StpUtil.getLoginId()))));
    }

    @PutMapping("user")
    public CommonResponse<?> editInfo(@Valid @RequestBody EditUserInfoRequest request) {
        StpUtil.checkLogin();
        userService.editInfo(StpUtil.getLoginIdAsString(), request.getPhone(), request.getBio());
        return CommonResponse.success();
    }
}