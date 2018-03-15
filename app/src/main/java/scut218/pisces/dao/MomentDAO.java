package scut218.pisces.dao;

import java.util.List;

import scut218.pisces.beans.Moment;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface MomentDAO {
    void insert(Moment moment);
    void delete(int id);
    void update(Moment moment);
    Moment queryById(int id);
    List<Moment> queryAll();
    List<Moment> queryByAuthorId(String id);
}
