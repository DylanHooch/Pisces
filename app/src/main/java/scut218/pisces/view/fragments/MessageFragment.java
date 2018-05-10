package scut218.pisces.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scut218.pisces.R;
import scut218.pisces.adapters.MessageAdapter;


public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private AppCompatActivity activity;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        findViews(view);
        recyclerView.setAdapter(new MessageAdapter(activity));
        //设置recyclerview的布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.HORIZONTAL));
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
}
