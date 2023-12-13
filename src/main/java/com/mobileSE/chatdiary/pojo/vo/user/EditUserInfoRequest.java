package com.mobileSE.chatdiary.pojo.vo.user;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUserInfoRequest {



    @NotNull
    private String userInfo;
}
