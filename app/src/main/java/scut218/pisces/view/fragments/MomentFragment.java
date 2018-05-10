package scut218.pisces.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import scut218.pisces.R;
import scut218.pisces.adapters.MomentAdapter;
import scut218.pisces.beans.Moment;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.utils.MomentUtil;
import scut218.pisces.view.PostActivity;

public class MomentFragment extends Fragment {


    private AppCompatActivity activity;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private MomentAdapter momentAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String uid;
    private RefreshTask mRefreshTask;
    private List<Moment> moments=new ArrayList<>();

    private OnMomentFragmentInteractionListener mListener;

    public MomentFragment() {
        // Required empty public constructor
    }

    public static MomentFragment newInstance(AppCompatActivity activity,String uid) {
        MomentFragment fragment = new MomentFragment();
        fragment.uid=uid;
        fragment.activity=activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_moment, container, false);

        fab=(FloatingActionButton)view.findViewById(R.id.fab_post);
        fab.show();
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, PostActivity.class);
                intent.putExtra("flag",1);
                activity.startActivity(intent);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(activity,PostActivity.class);
                intent.putExtra("flag",0);
                activity.startActivity(intent);
                return false;
            }
        });

        mRecyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_moment);
        momentAdapter=new MomentAdapter(moments,activity);
        mRecyclerView.setAdapter(momentAdapter);

        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefreshTask!=null)
                    return;
                Log.d("refresh moment","start");
                mRefreshTask=new RefreshTask(momentAdapter,uid);
                mRefreshTask.execute();
            }
        });

        //设置刷新动画颜色，可以设置4个
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_red_light,
                android.R.color.holo_orange_light,android.R.color.holo_green_light);
        //设置刷新动画位置
        mSwipeRefreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24, getResources().getDisplayMetrics()));
        //设置recyclerview的布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.HORIZONTAL));
        //添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMomentFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMomentFragmentInteractionListener) {
            mListener = (OnMomentFragmentInteractionListener) context;
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

    public interface OnMomentFragmentInteractionListener {
        void onMomentFragmentInteraction(Uri uri);
    }
    class RefreshTask extends AsyncTask<Void, Boolean, Boolean>{
        MomentAdapter momentAdapter;
        MomentUtil momentUtil;
        List<Moment> momentList;
        String id;
        public RefreshTask(MomentAdapter momentAdapter,String id) {
            this.momentAdapter=momentAdapter;
            this.id=id;
            momentUtil= UtilFactory.getMomentUtil();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            momentList=momentUtil.requestAllMoment();
            if(momentList==null)
                return false;
            momentAdapter.setData(momentList);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Log.d("refresh","success");
            }else{
                Log.e("refresh","failure");
            }
            mRefreshTask=null;
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            if(values[0]){
                momentAdapter.setData(momentList);
            }
            else
                Toast.makeText(activity,"网络超时",Toast.LENGTH_SHORT);
        }
    }
}
