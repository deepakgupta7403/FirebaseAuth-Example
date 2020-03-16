package com.example.firebaseauthexample.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

public class MyUtils {

    public static void showToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context mContext, String text, int length) {
        Toast.makeText(mContext, text, length).show();
    }

    public static int dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density + 0.5f);
    }

    public static int getPixels(Context context, float valueInDp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, r.getDisplayMetrics());
        return (int) px;
    }

    public static int getPixelsSp(Context context, int valueInSp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, r.getDisplayMetrics());
        return (int) px;
    }

    public static int getPixelsSp(Context context, float valueInSp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, r.getDisplayMetrics());
        return (int) px;
    }
}
