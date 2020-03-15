package com.example.firebaseauthexample.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.firebaseauthexample.R;
import com.google.android.material.textfield.TextInputEditText;

public class MobileAuthActivity extends AppCompatActivity {
    TextInputEditText tiet_title;
    LinearLayout ll_phone_number_input_view, ll_otp_verification_view;
    Button btn_send_verification;
    PinView pinView;
    TextView tv_resend_otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_auth_activity);

        initComponent();

    }

    private void initComponent() {
        tiet_title = findViewById(R.id.tiet_title);
        ll_phone_number_input_view = findViewById(R.id.ll_phone_number_input_view);
        ll_otp_verification_view = findViewById(R.id.ll_otp_verification_view);
        btn_send_verification = findViewById(R.id.btn_send_verification);
        pinView = findViewById(R.id.pinView);
        tv_resend_otp = findViewById(R.id.tv_resend_otp);
    }


}
