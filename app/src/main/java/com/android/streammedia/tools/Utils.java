package com.android.streammedia.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by litonghui on 2016/4/27.
 */
public class Utils {

    public static int getScreemWidth(Context context){
        return context!=null?context.getResources().getDisplayMetrics().widthPixels:0;
    }

    public static int getScreenHeight(Context context){
        return context!=null?context.getResources().getDisplayMetrics().heightPixels:0;
    }


    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int dx2dp(Context context, float dx) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dx / density + 0.5f);
    }
    public static boolean filterApp(int flags) {
        if ((flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
    public static TextView generateTextView(Context context, String text, int color, float size) {
        final TextView tv = new TextView(context);
        tv.setTextColor(color);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        tv.setText(text);
        return tv;
    }
    public static final FrameLayout.LayoutParams createWrapParams() {
        return createLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public static final FrameLayout.LayoutParams createLayoutParams(int width, int height) {
        return new FrameLayout.LayoutParams(width, height);
    }


    @SuppressLint("SimpleDateFormat")
    public static  String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    public static File createOrOpen(String filePath,String fileName) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(dir, fileName);
        return destFile;
    }
    public static String save(
            Bitmap bitmap,
            Bitmap.CompressFormat format,
            int quality,String filePath,String fileName) {

        if (!Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        File dir = new File(filePath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File destFile = new File(dir, fileName);
        return save(bitmap, format, quality, destFile);
    }
    public static String save(
            Bitmap bitmap,
            Bitmap.CompressFormat format,
            int quality, File destFile) {

        try {
            FileOutputStream out = new FileOutputStream(destFile);

            if (bitmap.compress(format, quality, out)) {
                out.flush();
                out.close();
            }
            return destFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
}
