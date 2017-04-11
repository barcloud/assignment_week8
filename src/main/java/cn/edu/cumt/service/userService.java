package cn.edu.cumt.service;

import cn.edu.cumt.dao.UserDao;
import cn.edu.cumt.dao.UserDaoimpl;
import cn.edu.cumt.entity.User;

import java.util.List;

/**
 * Created by fc751 on 2017/3/24.
 */
public class userService {
    private UserDao userDao;
    public void setUserDao(){
        this.userDao = new UserDaoimpl();
    }
    public List<User> getAll(){
        return userDao.getAll();
    }
    public boolean addCart(User user){
        return userDao.addUser(user);
    }
    public boolean updateCart(User oldUser,User newUser){
        return userDao.updateUser(oldUser, newUser);
    }
    public boolean deleteCar(User user){
        return userDao.deleteUser(user);
    }
    public User getByUser_name(User user){
        return userDao.getByUser_Name(user);
    }
}
