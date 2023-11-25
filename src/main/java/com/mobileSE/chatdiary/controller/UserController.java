package com.mobileSE.chatdiary.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.mapper.UserMapper;
import com.mobileSE.chatdiary.pojo.vo.user.EditUserInfoRequest;
import com.mobileSE.chatdiary.pojo.vo.user.LoginRequest;
import com.mobileSE.chatdiary.pojo.vo.user.RegisterRequest;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import com.mobileSE.chatdiary.svc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("session")
    public CommonResponse<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                return CommonResponse.error(BizError.INVALID_INPUT, fieldErrors.get(0).getDefaultMessage());
            }
        }
        // Throws BizException if auth failed.
        UserVO login = userService.login(request.getEmail(), request.getPassword());
        StpUtil.login(login.getId());
        return CommonResponse.success(login);
    }

    @PostMapping("user")
    public CommonResponse<?> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                return CommonResponse.error(BizError.INVALID_INPUT, fieldErrors.get(0).getDefaultMessage());
            }
        }
        try {
            userService.register(request.getUsername(), request.getPassword(), request.getEmail());
        } catch (BizException e) {
            return CommonResponse.error(e);
        } catch (Exception e) {
            return CommonResponse.error(BizError.UNKNOWN_ERROR);
        }
        return CommonResponse.success("注册成功");
    }

    //新增功能后端接口, 前端 发送图片, 后端保存, 作为用户头像
    @PostMapping("/user/avatar")
    public CommonResponse<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        StpUtil.checkLogin();
        try {
            String userId = StpUtil.getLoginIdAsString();
            userService.uploadAvatar(Long.valueOf(userId), file);
            return CommonResponse.success("头像上传成功");
        } catch (Exception e) {
            return CommonResponse.error(BizError.IMG_ERROR, "头像上传失败");
        }
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
        userService.editInfo(StpUtil.getLoginIdAsString());
        return CommonResponse.success();
    }
}