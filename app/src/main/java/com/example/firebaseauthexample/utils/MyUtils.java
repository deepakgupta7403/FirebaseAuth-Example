package com.example.firebaseauthexample.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class MyUtils {

    public static int dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density + 0.5f);
    }
}
