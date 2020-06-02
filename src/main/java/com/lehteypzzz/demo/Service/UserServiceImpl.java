package com.lehteypzzz.demo.Service;

import com.lehteypzzz.demo.Entity.User;
import com.lehteypzzz.demo.Dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("userServiceImpl")
public class UserServiceImpl {
    final
    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findOneByUserId(Integer userId){
        return userDao.findOneByUserId(userId);
    }
    public User getUserByUserName(String userName){
        return userDao.findOneByUserName(userName);
    }
    public List<User> getUserListByUserName(String userName){
        return userDao.findUserListByUserName(userName);
    }
//    查询好友
    public List<User> findFriendByIdList(List<Integer> userIdList){
        List<User> friendList = new ArrayList<>();
        for(Integer id:userIdList){
            friendList.add(userDao.findOneByUserId(id));
        }
        return friendList;
    }

    //登录时查询用户名是否存在
    public Boolean queryUserNameIsExist(String userName){
        User result = userDao.findOneByUserName(userName);
        return result != null;
    }

    //注册成功插入数据库
    public void saveUser(User user){
        userDao.save(user);
    }

    public User updateUser(User user, String userName, String password){
        user.setUserName(userName);
        user.setPassword(password);
        return userDao.save(user);
    }

    public void updateIsOnline(User user, Integer isOnline){
        user.setIsOnline(isOnline);
        userDao.save(user);
    }
}
