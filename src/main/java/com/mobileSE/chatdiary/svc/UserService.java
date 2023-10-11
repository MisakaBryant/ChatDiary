package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.entity.UserEntity;

public interface UserService {
    void login(String username, String password);
    void register(String username, String password, String phone);
    UserEntity findByUserName(String username);
    void editInfo(String username, String phone, String bio);
}
