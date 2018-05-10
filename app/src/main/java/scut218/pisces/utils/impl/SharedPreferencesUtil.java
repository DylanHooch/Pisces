package scut218.pisces.utils.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by Leebobo on 2017/5/14.
 */

public class SharedPreferencesUtil {
    private static String FILE_NAME;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferencesUtil instance;// 单例

    private SharedPreferencesUtil() {
    }
    /**
     * 初始化单例
     * @param context
     */
    public static synchronized SharedPreferencesUtil getInstance(Context context,final String file_name)
    {
        if (instance == null)
        {
            synchronized (SharedPreferencesUtil.class)
            {
                if (instance==null){
                    instance = new SharedPreferencesUtil();
                    instance.mSharedPreferences = context.getSharedPreferences(FILE_NAME,
                            Context.MODE_PRIVATE);
                    instance.FILE_NAME=file_name;
                    Log.i("TAG",FILE_NAME);
                }
            }
        }
        return instance;
    }

    /**
     * 保存数据
     *
     * @param key
     * @param data
     */
    public void saveData(String key, Object data) {
        String type = data.getClass().getSimpleName();

        Editor editor = mSharedPreferences.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }

        editor.commit();
    }

    /**
     * 得到数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public Object getData(String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return mSharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }
}
