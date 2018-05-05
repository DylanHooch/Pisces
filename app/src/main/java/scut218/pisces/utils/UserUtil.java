package scut218.pisces.utils;

import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface UserUtil {
    UserUtil getInstance();
    int register(User user);
    int login(String id,String pw);
    int updateProf(User user);
    int post(Moment moment);
}
