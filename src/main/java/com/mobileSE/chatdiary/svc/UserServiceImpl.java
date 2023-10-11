package com.mobileSE.chatdiary.svc;


import cn.dev33.satoken.secure.SaSecureUtil;
import com.mobileSE.chatdiary.dao.UserDao;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param phone    手机号
     */
    @Override
    public void register(String username, String password, String phone) {
        UserEntity user = userDao.findByUsername(username);

        if (user != null) {
            throw new BizException(BizError.USERNAME_EXISTS);
        }

        userDao.save(UserEntity.builder().username(username).password(SaSecureUtil.md5(password))
                .phone(phone).bio("").build());
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户实体对象
     */
    @Override
    public UserEntity findByUserName(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void login(String username, String password) {
        UserEntity user = userDao.findByUsername(username);
        if (user == null || !SaSecureUtil.md5(password).equals(user.getPassword())) {
            throw new BizException(BizError.INVALID_CREDENTIAL);
        }
    }

    /**
     * 编辑用户信息
     *
     * @param username  用户名
     * @param phone     手机号
     * @param bio       简介
     */
    @Override
    public void editInfo(String username, String phone, String bio) {
        UserEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new BizException(BizError.USER_NOT_FOUND);
        }
        userDao.save(user.setPhone(phone).setBio(bio));
    }
}
