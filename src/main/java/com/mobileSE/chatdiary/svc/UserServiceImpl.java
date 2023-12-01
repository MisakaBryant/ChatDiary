package com.mobileSE.chatdiary.svc;


import cn.dev33.satoken.secure.SaSecureUtil;
import com.mobileSE.chatdiary.dao.UserDao;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.dao.UserImageDao;
import com.mobileSE.chatdiary.mapper.UserMapper;
import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
import com.mobileSE.chatdiary.svc.service.ImageService;
import com.mobileSE.chatdiary.svc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ImageService imageService;
    private final UserImageDao userImageDao;


    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param email    邮箱
     */
    @Override
    public void register(String username, String password, String email) {
        UserEntity user = userDao.findByEmail(email);

        if (user != null) {
            throw new BizException(BizError.EMAIL_EXISTS);
        }
        userDao.save(UserEntity.builder().userInfo("用户现在啥也没写").username(username).password(SaSecureUtil.md5(password)).email(email).build());
    }


    /**
     * 用户登录
     *
     * @param email    邮箱
     * @param password 密码
     */
    @Override
    public UserVO login(String email, String password) {
        UserEntity user = userDao.findByEmail(email);
        if (user == null || !SaSecureUtil.md5(password).equals(user.getPassword())) {
            throw new BizException(BizError.INVALID_CREDENTIAL);
        }
        return UserMapper.INSTANCE.toUserVO(user);
    }

    @Override
    public void editInfo(Long userId, String useInfo) {
        UserEntity user = userDao.findById(userId).get();
        userDao.save(user.setUserInfo(useInfo));
    }

    @Override
    public void editUsername(Long userId, String username) {
        UserEntity user = userDao.findById(userId).get();
        userDao.save(user.setUsername(username));
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        UserEntity userEntity = userDao.findById(userId).get();
        UserVO userVO = UserMapper.INSTANCE.toUserVO(userEntity);
        userVO.setAvatarUrl(userImageDao.findById(userEntity.getAvatarUrlId()).get().getUrl());
        return userVO;
    }

    @Override
    public void editPassword(Long userId, String password) {
        UserEntity user = userDao.findById(userId).get();
        userDao.save(user.setPassword(SaSecureUtil.md5(password)));
    }

    //新增功能后端接口, 前端 发送图片, 后端保存, 作为用户头像
    @Override
    public void uploadAvatar(Long userId, MultipartFile file) throws IOException {
        imageService.uploadUserImageByUserId(file, userId);
    }
}
