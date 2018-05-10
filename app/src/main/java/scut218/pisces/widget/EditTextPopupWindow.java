package scut218.pisces.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import scut218.pisces.R;
import scut218.pisces.base.MyApplication;


public class EditTextPopupWindow extends PopupWindow {

    private Context context;
    private EditText commentEt;
    private Button sendBtn;
    private View.OnClickListener onClickListener;

    public EditTextPopupWindow(Context context){
        this.context=context;
        final View view=  LayoutInflater.from(context).inflate(R.layout.edittext_popupwindow,null);

        commentEt=(EditText)view.findViewById(R.id.commentEt);
        sendBtn=(Button)view.findViewById(R.id.sendBtn);

        this.setContentView(view);
        //设置窗体宽和高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        //设置外部可点击
        this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为white
        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //设置窗体动画
        this.setAnimationStyle(R.style.edittext_pop_anim);

        //设置软键盘弹出把整个view顶上去，不然会覆盖view
        setSoftInputMode(INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //如果不在弹出框范围销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.et_pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

 }

    public void showPopupWindow(View parent){
        if (!this.isShowing()){
            //添加listener
            commentEt.setOnClickListener(getOnClickListener());
            sendBtn.setOnClickListener(getOnClickListener());

            showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            //获取编辑框焦点
            commentEt.setFocusable(true);
            commentEt.setFocusableInTouchMode(true);

            //软键盘弹出
            InputMethodManager imm = (InputMethodManager) commentEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.showSoftInput(commentEt,InputMethodManager.SHOW_FORCED);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public String getComment(){
        String text;
        text=commentEt.getText().toString();
        if(!TextUtils.isEmpty(text)){
            return text;
        }else {
            Toast.makeText(MyApplication.getContext(),"请输入文字",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void clearAllText(){
        commentEt.getText().clear();
    }
}

