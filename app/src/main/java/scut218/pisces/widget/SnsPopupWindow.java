package scut218.pisces.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TableRow;


import java.util.ArrayList;

import scut218.pisces.R;
import scut218.pisces.utils.impl.DensityUtil;


public class SnsPopupWindow extends PopupWindow implements View.OnClickListener {
    private TableRow commentBtn;

    // 实例化一个矩形
    private Rect rect = new Rect();
    // 坐标的位置（x、y）
    private final int[] location = new int[2];
    // 弹窗子类项选中时的监听
    private OnItemClickListener itemClickListener;
    // 定义弹窗子类项列表
    private ArrayList<ActionItem> actionItems = new ArrayList<ActionItem>();

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public ArrayList<ActionItem> getActionItems() {
        return actionItems;
    }

    public void setActionItems(ArrayList<ActionItem> actionItems) {
        this.actionItems = actionItems;
    }


    public SnsPopupWindow(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.sns_popupwindow, null);
        commentBtn = (TableRow) view.findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(this);

        this.setContentView(view);
        this.setWidth(DensityUtil.dip2px(context, 150));
        this.setHeight(DensityUtil.dip2px(context, 40));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.sns_pop_anim);

        initItemData();
    }
    private void initItemData() {
        addAction(new ActionItem("赞"));
        addAction(new ActionItem("评论"));
    }

        public void showPopupWindow(View parent){
        parent.getLocationOnScreen(location);
        // 设置矩形的大小
        rect.set(location[0], location[1], location[0] + parent.getWidth(), location[1] + parent.getHeight());
        //digBtn.setText(actionItems.get(0).mTitle);
        if(!this.isShowing()){
            showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - this.getWidth()
                    , location[1] - ((this.getHeight() - parent.getHeight()) / 2));
        }else{
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.commentBtn:
                itemClickListener.onItemClick(actionItems.get(1), 1);
                break;
            default:
                break;
        }
    }

    /**
     * 添加子类项
     */
    public void addAction(ActionItem action) {
        if (action != null) {
            actionItems.add(action);
        }
    }

    /**
     * 功能描述：弹窗子类项按钮监听事件
     */
    public static interface OnItemClickListener {
        public void onItemClick(ActionItem item, int position);
    }

    public class ActionItem {
        // 定义图片对象
        public Drawable mDrawable;
        // 定义文本对象
        public CharSequence mTitle;

        public ActionItem(Drawable drawable, CharSequence title) {
            this.mDrawable = drawable;
            this.mTitle = title;
        }

        public ActionItem(CharSequence title) {
            this.mDrawable = null;
            this.mTitle = title;
        }

        public ActionItem(Context context, int titleId, int drawableId) {
            this.mTitle = context.getResources().getText(titleId);
            this.mDrawable = context.getResources().getDrawable(drawableId);
        }

        public ActionItem(Context context, CharSequence title, int drawableId) {
            this.mTitle = title;
            this.mDrawable = context.getResources().getDrawable(drawableId);
        }

        public void setItemTv(CharSequence tv) {
            mTitle = tv;
        }
    }

}
