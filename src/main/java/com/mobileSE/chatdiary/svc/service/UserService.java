package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserVO login(String username, String password);

    void register(String username, String password, String email);
    
    void editInfo(Long userId, String useInfo);

    void editUsername(Long userId, String username);

    UserVO getUserInfo(Long userId);

    void editPassword(Long userId, String password);


    void uploadAvatar(Long userId, MultipartFile file) throws IOException;
}
