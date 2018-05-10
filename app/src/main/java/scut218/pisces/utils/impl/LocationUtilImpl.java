package scut218.pisces.utils.impl;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import scut218.pisces.utils.LocationUtil;

/**
 * Created by Lenovo on 2018/5/10.
 */

public class LocationUtilImpl implements LocationUtil {
    private LocationManager locationManager;
    private Context context;
    private String provider;
    public LocationUtilImpl(Context context){
        this.context=context;//获取定位服务
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
    @Override
    public String getLocation() {

        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);

        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(context, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return null;
        }
        Location location=null;
        try {
            location = locationManager.getLastKnownLocation(provider);
        }catch (SecurityException e){
            e.printStackTrace();
            Log.e("location","no permission");
        }
        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            String loc =location.getLatitude()+","+
                    location.getLongitude();
            return loc;
        }
        return null;
    }

}
