package com.android.streammedia.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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

}
