package com.example.firebaseauthexample.model;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class AuthOptionModel implements Serializable {

   private int authIcon;
   private String authText;
   private int authBackGroundColor;
   private Class authClass;
   private int authTextColor;


    public AuthOptionModel(int authIcon, String authText, int authBackGroundColor, Class authClass, int authTextColor) {
        this.authIcon = authIcon;
        this.authText = authText;
        this.authBackGroundColor = authBackGroundColor;
        this.authClass = authClass;
        this.authTextColor = authTextColor;
    }

    public AuthOptionModel() {
    }

    public int getAuthIcon() {
        return authIcon;
    }

    public void setAuthIcon(int authIcon) {
        this.authIcon = authIcon;
    }

    public String getAuthText() {
        return authText;
    }

    public void setAuthText(String authText) {
        this.authText = authText;
    }

    public int getAuthBackGroundColor() {
        return authBackGroundColor;
    }

    public void setAuthBackGroundColor(int authBackGroundColor) {
        this.authBackGroundColor = authBackGroundColor;
    }

    public Class getAuthClass() {
        return authClass;
    }

    public void setAuthClass(Class authClass) {
        this.authClass = authClass;
    }

    public int getAuthTextColor() {
        return authTextColor;
    }

    public void setAuthTextColor(int authTextColor) {
        this.authTextColor = authTextColor;
    }
}
