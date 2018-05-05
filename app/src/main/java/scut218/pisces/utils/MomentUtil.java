package scut218.pisces.utils;

import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Moment;

/**
 * Created by Lenovo on 2018/5/5.
 */

public interface MomentUtil {
    int post(Moment moment);
    int delete(Moment moment);
    int addComment(Comment comment,int momentId);
    int deletecomment(int commentId,int momentId);

}
