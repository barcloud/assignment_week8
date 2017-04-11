package cn.edu.cumt.dao;

import cn.edu.cumt.entity.User;

import java.util.List;

/**
 * Created by fc751 on 2017/3/24.
 */
public interface UserDao {
    public List<User> getAll();
    public boolean addUser(User user);
    public boolean updateUser(User oldUser, User newUser);
    public boolean deleteUser(User user);
    public User getByUser_Name(User user);
}
