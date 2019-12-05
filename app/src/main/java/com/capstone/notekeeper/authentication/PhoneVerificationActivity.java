package com.capstone.notekeeper.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.notekeeper.CommonFiles.MainActivity;
import com.capstone.notekeeper.R;
import com.firebase.ui.auth.data.model.PhoneNumberVerificationRequiredException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private String mobileNumber;
    private EditText mMobileNumber, mVerificationCode, mCountry_code;
    private TextView mChangeNumber, mMobileDesc;
    private Button mSendCode, mResendCode, mVerifyCode;
    private LinearLayout mButtonContainer;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private TextInputLayout mCountryCode,mMobileNumberContainer,mVerifyContainer;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mSharedPreferences = getSharedPreferences("login_data",MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.mobile_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        mMobileNumber = findViewById(R.id.mobile_number);
        mVerificationCode = findViewById(R.id.mobile_verification_code);
        mSendCode = findViewById(R.id.btn_send_verification_code);
        mResendCode = findViewById(R.id.btn_resend_code);
        mVerifyCode = findViewById(R.id.btn_verify_code);
        mMobileDesc = findViewById(R.id.mobile_desc);
        mButtonContainer = findViewById(R.id.container_4);
        mChangeNumber = findViewById(R.id.btn_change_mobile_number);
        mMobileNumberContainer = findViewById(R.id.container_2);
        mCountryCode = findViewById(R.id.container_1);
        mVerifyContainer = findViewById(R.id.container_3);


        setSendCode();
        setResendCode();
        setVerifyCode();
        setChangeNumber();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(PhoneVerificationActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT)
                            .show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(PhoneVerificationActivity.this, "SMS quota Exceeded", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCodeSent(
                    @NonNull String verificationId,
                    @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(PhoneVerificationActivity.this, "Code Sent", Toast.LENGTH_SHORT)
                        .show();
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            mFirebaseAuth.signOut();
            finish();
        }
        return true;
    }

    public void setChangeNumber(){
        mChangeNumber.setOnClickListener(pView -> {
            mMobileNumberContainer.setVisibility(View.VISIBLE);
            mCountryCode.setVisibility(View.VISIBLE);
            mMobileNumber.setText("");
            mMobileDesc.setVisibility(View.VISIBLE);
            mSendCode.setVisibility(View.VISIBLE);
            mChangeNumber.setVisibility(View.GONE);
            mVerifyContainer.setVisibility(View.GONE);
            mButtonContainer.setVisibility(View.GONE);
        });
    }
    public void setResendCode() {
        mResendCode.setOnClickListener(pView -> resendVerificationCode(mobileNumber, mResendToken));

    }

    public void setVerifyCode() {
        mVerifyCode.setOnClickListener(pView -> {
            String code = String.valueOf(mVerificationCode.getText());
            if (!TextUtils.isEmpty(code)) {
                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });

    }

    public void setSendCode() {
        mSendCode.setOnClickListener(pView -> {
            mobileNumber = String.valueOf(mMobileNumber.getText());
            if (!TextUtils.isEmpty(mobileNumber) && mobileNumber.length() == 10) {
                mMobileNumberContainer.setVisibility(View.GONE);
                mCountryCode.setVisibility(View.GONE);
                mMobileDesc.setVisibility(View.GONE);
                mSendCode.setVisibility(View.GONE);
                mChangeNumber.setVisibility(View.VISIBLE);
                mVerifyContainer.setVisibility(View.VISIBLE);
                mButtonContainer.setVisibility(View.VISIBLE);
                startPhoneNumberVerification("+91"+mobileNumber);
            } else {
                mMobileNumber.setError("Invalid number");
            }
        });
    }

    public void startPhoneNumberVerification(String phoneNumber) {
        mobileNumber = phoneNumber;
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                        phoneNumber, // Phone number to verify
                        60, // Timeout duration
                        TimeUnit.SECONDS, // Unit of timeout
                        this, // Activity (for callback binding)
                        mCallbacks); // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(
            String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                        phoneNumber, // Phone number to verify
                        60, // Timeout duration
                        TimeUnit.SECONDS, // Unit of timeout
                        this, // Activity (for callback binding)
                        mCallbacks, // OnVerificationStateChangedCallbacks
                        token); // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        if (mFirebaseAuth.getCurrentUser() != null) {
            mCurrentUser = mFirebaseAuth.getCurrentUser();
            mCurrentUser.updatePhoneNumber(credential)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void pVoid) {
                            mSharedPreferences.edit().putBoolean("phone",true).apply();
                            Toast.makeText(PhoneVerificationActivity.this, "Successfully logged In.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PhoneVerificationActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception pE) {
                    Toast.makeText(PhoneVerificationActivity.this, "Failed :" + pE.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }
}
