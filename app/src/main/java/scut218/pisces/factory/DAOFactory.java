package scut218.pisces.factory;

import scut218.pisces.dao.CommentDAO;
import scut218.pisces.dao.MomentDAO;
import scut218.pisces.dao.UserDAO;

/**
 * Created by Lenovo on 2018/3/14.
 */

public class DAOFactory {
    public static final int Mysql= 1;
    public static final int Sqlite = 2;
    public CommentDAO getCommentDAO()
    {
        return null;
    }
    public UserDAO getUserDAO()
    {
        return null;
    }
    public MomentDAO getMomentDAO()
    {
        return null;
    }
}
