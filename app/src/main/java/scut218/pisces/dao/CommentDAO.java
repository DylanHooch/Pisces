package scut218.pisces.dao;

import java.util.List;

import scut218.pisces.beans.Comment;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface CommentDAO {
    void insert(Comment comment);
    void delete(int id);
    Comment queryById(int id);
    List<Comment> queryAll();
    List<Comment> queryByMomentId(int id);
}
