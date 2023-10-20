package com.mobileSE.chatdiary.svc;


import cn.dev33.satoken.secure.SaSecureUtil;
import com.mobileSE.chatdiary.dao.UserDao;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.mapper.UserMapper;
import com.mobileSE.chatdiary.pojo.entity.UserEntity;
import com.mobileSE.chatdiary.pojo.vo.user.UserVO;
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
     * @param email    邮箱
     */
    @Override
    public void register(String username, String password, String email) {
        UserEntity user = userDao.findByEmail(email);

        if (user != null) {
            throw new BizException(BizError.EMAIL_EXISTS);
        }
        userDao.save(UserEntity.builder().username(username).password(SaSecureUtil.md5(password)).email(email).build());
    }

    /**
     * 根据查询用户信息
     *
     * @param email 邮箱
     * @return 用户实体对象
     */
    @Override
    public UserEntity findByUserName(String email) {
        return userDao.findByEmail(email);
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

    /**
     * 编辑用户信息
     *
     * @param email 邮箱
     */
    @Override
    public void editInfo(String email) {
        UserEntity user = userDao.findByEmail(email);
        if (user == null) {
            throw new BizException(BizError.USER_NOT_FOUND);
        }
        userDao.save(user.setEmail(email));
    }
}
