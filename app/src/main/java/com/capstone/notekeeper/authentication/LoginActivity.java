package com.capstone.notekeeper.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capstone.notekeeper.CommonFiles.MainActivity;
import com.capstone.notekeeper.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final int GOOGLE_SIGNIN_CODE = 23;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView  btnReset;
    private Button btnLogin,btnSignup;
    private Button btnGoogle;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        mSharedPreferences= getSharedPreferences("login_data",MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnGoogle = findViewById(R.id.loginGoogle);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);
        auth = FirebaseAuth.getInstance();

        goToSignUpActivity();
        goToResetPasswordActivity();
        signInWithGoogleBtn();
        signInWithEmailAndPAssword();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkVerificationStatus();
    }

    private void checkVerificationStatus() {
        if (auth.getCurrentUser() != null) {
            boolean verified = mSharedPreferences.getBoolean("phone",false);
            boolean logged = mSharedPreferences.getBoolean("login",false);
//            Toast.makeText(this, ""+logged+" "+verified, Toast.LENGTH_SHORT).show();
            if(verified) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            else if(logged){
                startActivity(new Intent(LoginActivity.this, PhoneVerificationActivity.class));
            }
            else
                auth.signOut();
        }
    }

    private void goToSignUpActivity() {
        btnSignup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    private void goToResetPasswordActivity() {
        btnReset.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class)));
    }

    private void signInWithGoogleBtn() {
        btnGoogle.setOnClickListener(view -> {
            List<AuthUI.IdpConfig> providers = Collections.singletonList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    GOOGLE_SIGNIN_CODE);
        });
    }

    private void signInWithEmailAndPAssword() {
        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            mSharedPreferences.edit().putBoolean("login",true).apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGNIN_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                mSharedPreferences.edit().putBoolean("login",true).apply();
                Intent intent = new Intent(LoginActivity.this,PhoneVerificationActivity.class);
                startActivity(intent);
            } else {
                if (response == null) {
                    Toast.makeText(this, "Cancelled by user!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Login Failed: " + response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

