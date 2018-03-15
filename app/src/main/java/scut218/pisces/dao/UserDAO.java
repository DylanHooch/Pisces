package scut218.pisces.dao;

import java.util.List;

import scut218.pisces.beans.User;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface UserDAO {
    void insert(User user);
    void delete(String id);
    void update(User user);
    User queryById(String id);
    List<User> queryAll();
}
