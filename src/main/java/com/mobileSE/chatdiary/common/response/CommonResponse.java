package com.mobileSE.chatdiary.common.response;

import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse<T> {
    /**
     * custom status code
     */
    private int code;
    /**
     * status message
     */
    private String msg;
    /**
     * data to be returned
     */
    private T data;

    private int httpCode;

    public static <T> CommonResponse<T> success() {
        return success(null);
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, 200);
    }

    public static <T> CommonResponse<T> success(int httpCode) {
        return success(null, httpCode);
    }

    public static <T> CommonResponse<T> success(T data, int httpCode) {
        return new CommonResponse<>(0, "success", data, httpCode);
    }

    public static <T> CommonResponse<T> error(BizError type) {
        return new CommonResponse<>(type.getCode(), type.getMessage(), null, type.getHttpCode());
    }

    public static <T> CommonResponse<T> error(BizError type, String msg) {
        return new CommonResponse<>(type.getCode(), msg, null, type.getHttpCode());
    }

    public static <T> CommonResponse<T> error(BizException exception) {
        return new CommonResponse<>(exception.getCode(), exception.getMessage(), null, exception.getHttpCode());
    }
}
