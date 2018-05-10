package scut218.pisces.utils.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import scut218.pisces.R;
import scut218.pisces.base.MyApplication;
import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.utils.UserUtil;

public class ImageUtil {


    public static void displayImage(ImageView view, String path){
        Glide.with(MyApplication.getContext())
                .load(path)
                .placeholder(R.drawable.loading_out)
                .error(R.drawable.loading_out)
                .into(view);
    }

    public static void loadIcon(ImageView view, String id)
    {
        User user;
        if((user=User.userMap.get(id))==null)
        {
            UserUtil userUtil= UtilFactory.getUserUtil();
            user=userUtil.requestProf(id).get(0);
        }

        Glide.with(MyApplication.getContext())
                .load(user.getPhotoPath())
                .placeholder(R.drawable.loading_out)
                .error(R.drawable.loading_out)
                .into(view);
    }


    /**获取图片的高斯模糊
     * @param BITMAP_SCALE 图片缩小率，先缩小图片后处理放置OOM
     * @param 最大模糊度(在0.0到25.0之间)
     */
    private static final float BITMAP_SCALE = 0.125f;
    private static final float BLUR_RADIUS = 3f;
    public static Bitmap blurBitmap(Context context, Bitmap bitmap,float blurRadius) {
        Bitmap image = ImageUtil.RGB565toARGB888(bitmap);
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);
        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }


    /**
     * bitmap  转换 ARGB_8888
     * @param img 原始bitmap
     * @return  转换后bitmap
     */
    public static Bitmap RGB565toARGB888(Bitmap img) {
        int numPixels = img.getWidth()* img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

}
