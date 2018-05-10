package scut218.pisces.loader;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import scut218.pisces.beans.Friend;
import scut218.pisces.beans.User;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.utils.UserUtil;

/**
 * Created by Lenovo on 2018/5/10.
 */

public class PreLoadTask extends AsyncTask<Void,Void,Boolean> {
    public boolean isLoading=false;
    public boolean fail=false;
    @Override
    protected Boolean doInBackground(Void... voids) {
        isLoading=true;
        UserUtil userUtil= UtilFactory.getUserUtil();
        User.me=userUtil.requestProf(userUtil.getMyId()).get(0);

        List<Friend> friends= userUtil.requestFriends();
        if(friends==null)
        {
            return false;
        }
        User.friendList.addAll(friends);
        return true;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        isLoading=false;
        if(aBoolean)
        {
            fail=true;
        }else fail=false;
    }
}
