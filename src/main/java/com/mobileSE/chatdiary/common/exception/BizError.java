package com.mobileSE.chatdiary.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BizError {
    UNKNOWN_ERROR(200000, "未知错误", 400),
    EMAIL_EXISTS(200001, "邮箱已存在", 400),
    INVALID_CREDENTIAL(200002, "用户名或密码错误", 400),
    USER_NOT_FOUND(200003, "用户不存在", 400),
    INVALID_INPUT(200004, "不合理输入", 400),
    GPT_API_ERROR(200005, "GPT处理错误", 400),
    REQUEST_ERROR(200006, "图片请求有误", 400),
    IMG_ERROR(200007, "图片上传有误", 400);
    final int code;
    final String message;
    final int httpCode;
}
