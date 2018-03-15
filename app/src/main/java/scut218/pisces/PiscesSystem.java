package scut218.pisces;

import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;

/**
 * Created by Lenovo on 2018/3/14.
 * designed in singleton pattern
 */

public interface PiscesSystem {
    public int register(User user);
    public int login(String id,String pw);
    public int post(Moment moment);
}
