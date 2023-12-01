package com.mobileSE.chatdiary.pojo.vo.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserInfoRequest {



    @NotNull
    private String userInfo;
}
