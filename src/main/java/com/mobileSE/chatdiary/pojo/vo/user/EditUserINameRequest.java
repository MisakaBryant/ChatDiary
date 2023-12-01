package com.mobileSE.chatdiary.pojo.vo.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class EditUserINameRequest {


    @NotNull
    @Size(min = 4, max = 16, message = "用户名长度必须在 4-16 之间")
    @Pattern(regexp = "^[a-z\\d-_]*$", message = "用户名只能包含小写字母,数字,下划线和连字符")
    private String username;
}
