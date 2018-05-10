package scut218.pisces.utils.impl;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Leebobo on 2017/5/9.
 * 尺寸单位转换
 */

public class DensityUtil {

//    dip转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

//    px转dip
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 获取手机的密度*/
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }
}
