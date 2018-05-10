package scut218.pisces.utils;

import java.util.List;

import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Moment;

/**
 * Created by Lenovo on 2018/5/5.
 */

public interface MomentUtil {
    int post(Moment moment);
    int delete(int momentId);
    int addComment(Comment comment);
    int deletecomment(int commentId);
    List<Moment> requestAllMoment();//所有附近的人的状态
    List<Moment> requestMomentByAuthor(String uid);//某个人的状态
}
