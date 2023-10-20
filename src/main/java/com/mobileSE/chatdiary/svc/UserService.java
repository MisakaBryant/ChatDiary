package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;

public interface UserService {
    UserVO login(String username, String password);

    void register(String username, String password, String email);

    UserEntity findByUserName(String username);

    void editInfo(String username);
}
