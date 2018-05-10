package scut218.pisces.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.util.Util;

import java.util.List;

import scut218.pisces.R;
import scut218.pisces.adapters.MessageAdapter;
import scut218.pisces.beans.Friend;
import scut218.pisces.beans.User;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.loader.PreLoadTask;
import scut218.pisces.utils.UserUtil;


public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private AppCompatActivity activity;
    private List<Friend> friends;
    private List<User> users;
    private MessageAdapter adapter;
    private loadTask mLoadTask;

    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance(AppCompatActivity activity) {
        MessageFragment fragment = new MessageFragment();
        fragment.activity=activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mLoadTask=new loadTask();
        mLoadTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        findViews(view);
        adapter=new MessageAdapter(activity);
        recyclerView.setAdapter(adapter);
        //设置recyclerview的布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.HORIZONTAL));
        //添加动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void findViews(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_message);
    }
    class loadTask extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... voids) {
            while(PreLoadTask.isLoading);
            if(PreLoadTask.fail)
                return false;
            friends= User.friendList;
            Log.e("loadTask","success");
            publishProgress();
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.setData(friends);
        }
    }
}
