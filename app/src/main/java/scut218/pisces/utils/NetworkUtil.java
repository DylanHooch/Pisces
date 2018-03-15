package scut218.pisces.utils;

import scut218.pisces.beans.Request;
import scut218.pisces.beans.Response;

/**
 * Created by Lenovo on 2018/3/14.
 */

public interface NetworkUtil {
    int send(Request request,String address,String port);
    int listen(String port);
    Response recv();
}
