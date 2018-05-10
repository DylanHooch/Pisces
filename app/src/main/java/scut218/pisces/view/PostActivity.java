package scut218.pisces.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import scut218.pisces.R;
import scut218.pisces.beans.Moment;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.utils.LocationUtil;
import scut218.pisces.utils.MomentUtil;

/**
 * Created by Administrator on 2017/4/25.
 */

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private MomentUtil momentUtil;
    private LocationUtil locationUtil;

    private final static int TYPE_TEXT=1;
    private final static int TYPE_IMAGE=2;

    private boolean isImage=false;//默认文字
    private int type;
    private EditText momentEditText;
    private TextView locationTv;
    private Button publishBtn;
    private Toolbar toolbar;
    private Button backBtn;
    private ImageView imageView;
    private String imagePath;

    private ProgressDialog mLoadingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_moment);
        //findview
        momentEditText =(EditText)findViewById(R.id.topicEt) ;
        locationTv=(TextView) findViewById(R.id.publish_location_tv);
        publishBtn=(Button)findViewById(R.id.send_Btn);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        backBtn=(Button)findViewById(R.id.back_Btn) ;
        setSupportActionBar(toolbar);
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoadingDialog.setMessage("发送中...");
        mLoadingDialog.setCancelable(false);

        imageView=(ImageView)findViewById(R.id.post_img_view);


        //setlistener
        backBtn.setOnClickListener(this);
        publishBtn.setOnClickListener(this);
        publishBtn.setClickable(false);
        momentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    publishBtn.setClickable(true);
                }else  publishBtn.setClickable(false);

            }
        });

        momentUtil= UtilFactory.getMomentUtil();
        locationUtil=UtilFactory.getLocationUtil(this);
        locationTv.setText(locationUtil.getLocation());

        isImage=this.getIntent().getIntExtra("flag",1)==1? true:false;

        /*直接开始选择图片*/
        if(isImage){
            imageView.setVisibility(View.VISIBLE);
            ImagePicker.getInstance().setSelectLimit(1);//只能选择一张
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, TYPE_IMAGE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == TYPE_IMAGE) {
                ImageItem imageItem = ((ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS)).get(0);
                imagePath=imageItem.path;
                Glide.with(this).load(imagePath).into(imageView);
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_Btn:
                type=isImage?1:0;
                Moment moment=new Moment();
                moment.setAuthorId(UtilFactory.getUserUtil().getMyId());
                moment.setLocation(locationUtil.getLocation());
                moment.setPath(imagePath);
                moment.setText(momentEditText.getText().toString());
                moment.setType(type);
                moment.setTime(new Timestamp(new Date().getTime()));
                momentUtil.post(moment);
                break;
            case R.id.back_Btn:
                PostActivity.this.finish();
            default:break;
        }
    }
}
