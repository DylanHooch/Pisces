package scut218.pisces.utils;

import java.util.List;

import scut218.pisces.beans.Friend;
import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface UserUtil {
    int register(User user);
    int login(String id, String pw);
    int updateProf(User user);
    int addFriend(Friend friend);
    int deleteFriend(String friendId);
    List<Friend> requestFriends();
    List<User>  requestProf(String id);
    String getMyId();


}
