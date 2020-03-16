package com.example.firebaseauthexample.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseauthexample.R;
import com.example.firebaseauthexample.customview.OTPView.OTPListener;
import com.example.firebaseauthexample.customview.OTPView.OtpTextView;
import com.example.firebaseauthexample.utils.MyUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileAuthActivity extends AppCompatActivity {
    public static final int PHONE_HINT = 100;
    public static final String TAG = MobileAuthActivity.class.getName();

    AppCompatActivity mActivity;
    TextInputEditText tiet_mobile_number;
    LinearLayout ll_phone_number_input_view, ll_otp_verification_view;
    Button btn_send_verification;
    OtpTextView otp_view;
    TextView tv_resend_otp;
    boolean isOTPViewVisible = false;
    FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_auth_activity);
        mActivity = this;

        initComponent();

        try {
            contactHint();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }



        btn_send_verification.setOnClickListener(v -> {
            if (!validatePhoneNumber()) {
                return;
            }
            String finalPhoneNumber;
            String phoneNumber = tiet_mobile_number.getText().toString();
            if (phoneNumber.contains("+91")) {
                finalPhoneNumber = phoneNumber.replace("+91", "");
            } else {
                finalPhoneNumber = phoneNumber;
            }

            callBackMethod();
            startPhoneNumberVerification("+91" + finalPhoneNumber);
        });

        otp_view.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                signInWithPhoneAuthCredential(credential);
                Toast.makeText(getApplicationContext(), "The OTP is " + otp, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void initComponent() {
        tiet_mobile_number = findViewById(R.id.tiet_mobile_number);
        ll_phone_number_input_view = findViewById(R.id.ll_phone_number_input_view);
        ll_otp_verification_view = findViewById(R.id.ll_otp_verification_view);
        btn_send_verification = findViewById(R.id.btn_send_verification);
        otp_view = findViewById(R.id.otp_view);
        tv_resend_otp = findViewById(R.id.tv_resend_otp);
    }

    private void contactHint() throws IntentSender.SendIntentException {
        GoogleApiClient apiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                apiClient, hintRequest);
        startIntentSenderForResult(intent.getIntentSender(),
                PHONE_HINT, null, 0, 0, 0);
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = tiet_mobile_number.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            tiet_mobile_number.setError("Invalid phone number");
            return false;
        } else if (TextUtils.isEmpty(tiet_mobile_number.getText()) || (tiet_mobile_number.getText().length() != 10 && tiet_mobile_number.getText().length() != 13)) {
            tiet_mobile_number.setError("Number should be 10 or 13 digits only");
            return false;
        }
        return true;
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,                          // Phone number to verify
                60,                                // Timeout duration
                TimeUnit.SECONDS,                    // Unit of timeout
                mActivity,                           // Activity (for callback binding)
                mCallbacks);                         // OnVerificationStateChangedCallbacks
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            MyUtils.showToast(mActivity, "Signin Successfully", Toast.LENGTH_LONG);


                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                MyUtils.showToast(mActivity, "Invalid code.", Toast.LENGTH_LONG);
                            }
                        }
                    }
                });
    }

    private void callBackMethod() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.w(TAG, "onVerificationCompleted:" + phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);


                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    tiet_mobile_number.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(mActivity, "Quota is exceeded, Please contact to admin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.


                ll_phone_number_input_view.setVisibility(View.GONE);
                ll_otp_verification_view.setVisibility(View.VISIBLE);
                isOTPViewVisible = true;
                otp_view.requestFocusOTP();

                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;


            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHONE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                String phoneNumber = null;
                if (credential.getId().contains("+91")) {
                    phoneNumber = credential.getId().replace("+91", "");
                } else {
                    phoneNumber = credential.getId();
                }
                tiet_mobile_number.setText(phoneNumber);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isOTPViewVisible) {
            finish();
            Intent intent = new Intent(mActivity,MobileAuthActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
    }


}
