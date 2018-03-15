package scut218.pisces.dbc;

import java.sql.Connection;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface DBConnector {
    Connection getConnection();
    void close();
}
