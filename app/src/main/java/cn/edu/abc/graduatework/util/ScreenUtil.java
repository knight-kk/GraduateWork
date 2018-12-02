package cn.edu.abc.graduatework.util;


import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int getScreenHeightNotStatusBar(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return displayMetrics.heightPixels - context.getResources().getDimensionPixelSize(resourceId);
        }
        return displayMetrics.heightPixels;
    }


    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
