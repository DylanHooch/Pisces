package scut218.pisces.widget;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.BaseMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import scut218.pisces.R;
import scut218.pisces.base.MyApplication;
import scut218.pisces.beans.Comment;


public class CommentListView extends LinearLayout{

    private List<Comment> list;
    private LayoutInflater layoutInflater ;

    private OnItemClickListener onItemClickListener;
    private OnSpanClickListener onSpanClickListener;

    private final static int FONT_FRONT_COLOR = R.color.username_font_front_color;
    private final static int BACKGROUND=R.color.listview_bg_color;

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnSpanClickListener getOnSpanClickListener() {
        return onSpanClickListener;
    }

    public void setOnSpanClickListener(OnSpanClickListener onSpanClickListener) {
        this.onSpanClickListener = onSpanClickListener;
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnSpanClickListener{
        public void onSpanClick(int position);
    }

    public void notifyDataSetChanged(List<Comment> data){
        this.list=data;
        showList();
    }

    private void showList() {
        removeAllViews();
        if(list!=null&&list.size()>0) {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(MyApplication.getContext().getResources().getColor(BACKGROUND));
            for (int i = 0; i < list.size(); ++i) {
                View view = getView(i);
                if (view == null) {
                }
                addView(view, i, layoutParams);
            }
        }
    }

    private View getView(final int index) {
        if (layoutInflater ==null){
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.comment_item,null, false);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        final CommentMovementMethod movementMethod=new CommentMovementMethod();
        final Comment comment=list.get(index);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String id=comment.getUserId();



        builder.append(setClickableSpan(comment.getUserId(), index));
        builder.append(": ");
        //comment
        builder.append(comment.getText());
        commentTv.setText(builder);
        commentTv.setMovementMethod(movementMethod);
        commentTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movementMethod.isPassToTv()){
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(index);
                    }
                }
            }
        });
        return convertView;
    }

    private SpannableString setClickableSpan(final String textStr, final int position) {
        SpannableString SpanText = new SpannableString(textStr);

        //设置点击事件
        SpanText.setSpan(new ClickableSpan() {
                             @Override
                             public void onClick(View widget) {
                                 if(onSpanClickListener!=null){
                                     onSpanClickListener.onSpanClick(position);
                                 }
                             }
                             public void updateDrawState(TextPaint ds) {
                                 ds.setUnderlineText(false);
                             }
                         }, 0, SpanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置前景色
        SpanText.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(FONT_FRONT_COLOR)),
                0,SpanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return SpanText;
    }


    class CommentMovementMethod extends BaseMovementMethod {

        private final static int DEFAULT_COLOR_ID = R.color.listview_clickable_color;
        private final static int DEFAULT_CLICKABLEA_COLOR_ID = R.color.default_clickable_color;
        /**整个textView的背景色*/
        private int textViewBgColor;
        /**点击部分文字时部分文字的背景色*/
        private int clickableSpanBgClor;

        private BackgroundColorSpan mBgSpan;
        private ClickableSpan[] mClickLinks;
        private boolean isPassToTv = true;
        /**
         * true：响应textview的点击事件， false：响应设置的clickableSpan事件
         */
        public boolean isPassToTv() {
            return isPassToTv;
        }
        private void setPassToTv(boolean isPassToTv){
            this.isPassToTv = isPassToTv;
        }

        public CommentMovementMethod(){
            this.textViewBgColor = MyApplication.getContext().getResources().getColor(DEFAULT_COLOR_ID);
            this.clickableSpanBgClor = MyApplication.getContext().getResources().getColor(DEFAULT_CLICKABLEA_COLOR_ID);
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer,
                                    MotionEvent event) {

            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                mClickLinks = buffer.getSpans(off, off, ClickableSpan.class);
                if(mClickLinks.length > 0){
                    // 点击的是Span区域，不要把点击事件传递
                    setPassToTv(false);
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(mClickLinks[0]),
                            buffer.getSpanEnd(mClickLinks[0]));
                    //设置点击区域的背景色
                    mBgSpan = new BackgroundColorSpan(clickableSpanBgClor);
                    buffer.setSpan(mBgSpan,
                            buffer.getSpanStart(mClickLinks[0]),
                            buffer.getSpanEnd(mClickLinks[0]),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }else{
                    setPassToTv(true);
                    // textview选中效果
                    widget.setBackgroundColor(textViewBgColor);
                }

            }else if(action == MotionEvent.ACTION_UP){
                if(mClickLinks.length > 0){
                    mClickLinks[0].onClick(widget);
                    if(mBgSpan != null){//移除点击时设置的背景span
                        buffer.removeSpan(mBgSpan);
                    }
                }else{

                }
                Selection.removeSelection(buffer);
                widget.setBackgroundResource(R.color.transparent);
            }else if(action == MotionEvent.ACTION_MOVE){
                //这种情况不用做处理
            }else{
                if(mBgSpan != null){//移除点击时设置的背景span
                    buffer.removeSpan(mBgSpan);
                }
                widget.setBackgroundResource(R.color.transparent);
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }
}
