package scut218.pisces.factory;

import android.content.Context;

import scut218.pisces.utils.LocationUtil;
import scut218.pisces.utils.MomentUtil;
import scut218.pisces.utils.NetworkUtil;
import scut218.pisces.utils.UserUtil;
import scut218.pisces.utils.impl.ClientNetworkImpl;
import scut218.pisces.utils.impl.LocationUtilImpl;
import scut218.pisces.utils.impl.MomentUtilImpl;
import scut218.pisces.utils.impl.UserUtilImpl;

/**
 * Created by Lenovo on 2018/5/5.
 */

public class UtilFactory {
    public static UserUtil getUserUtil(){
        return new UserUtilImpl();
    }
    public static NetworkUtil getNetworkUtil(){
        return ClientNetworkImpl.getsingleNetworkUtilImpl();
    }
    public static MomentUtil getMomentUtil(){return new MomentUtilImpl();}
    public static LocationUtil getLocationUtil(Context context){return new LocationUtilImpl(context);}

}
