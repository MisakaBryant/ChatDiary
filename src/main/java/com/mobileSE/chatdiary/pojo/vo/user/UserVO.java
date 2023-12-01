package com.mobileSE.chatdiary.pojo.vo.user;

import lombok.Data;

@Data
public class UserVO {
    private String username;
    private String email;
    private Long id;
    private String userInfo;
    private String avatarUrl;
}
