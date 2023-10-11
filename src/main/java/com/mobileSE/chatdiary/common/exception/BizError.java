package com.mobileSE.chatdiary.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BizError {

    USERNAME_EXISTS(200001, "用户名已存在", 400),
    INVALID_CREDENTIAL(200002, "用户名或密码错误", 400),
    USER_NOT_FOUND(200003, "用户不存在", 400);

    final int code;
    final String message;
    final int httpCode;
}
