package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserVO login(String username, String password);

    void register(String username, String password, String email);

    UserEntity findByUserName(String username);

    void editInfo(String username);

    void uploadAvatar(Long userId, MultipartFile file) throws IOException;
}
