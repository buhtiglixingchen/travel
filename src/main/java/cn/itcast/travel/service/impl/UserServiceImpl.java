package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        User u = userDao.findByUsername(user.getUsername());
        if (null != u) {
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);
        String content = "<a href='http://localhost/travel/usesr/active?code=" + user.getCode() + "'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活travel账号");
        return true;
    }

    @Override
    public boolean active(String code) {
        User user = userDao.findByCode(code);
        if (user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User u) {
        return userDao.findByUsernameAndPassword(u.getUsername(),u.getPassword());
    }
}
