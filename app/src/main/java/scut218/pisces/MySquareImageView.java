package scut218.pisces;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Lenovo on 2018/3/24.
 */

public class MySquareImageView extends ImageView {
    public MySquareImageView(Context context)
    {
        super(context);
    }
    public MySquareImageView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }
    public MySquareImageView(Context context,AttributeSet attrs,int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
