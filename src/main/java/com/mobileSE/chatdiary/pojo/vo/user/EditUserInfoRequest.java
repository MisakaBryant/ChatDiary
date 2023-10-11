package com.mobileSE.chatdiary.pojo.vo.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserInfoRequest {

    @NotNull
    @Size(min = 11, max = 11, message = "手机号长度必须为11")
    @Pattern.List({
            @Pattern(regexp = "^\\d{11}$", message = "手机号格式错误"),
    })
    private String phone;

    @NotNull
    private String bio;
}
