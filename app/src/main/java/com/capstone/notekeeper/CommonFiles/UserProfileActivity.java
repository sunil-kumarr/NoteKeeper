package com.capstone.notekeeper.CommonFiles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.capstone.notekeeper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class UserProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private EditText mUserName,mUserBio,mUserMobileNumber,mUserEmailAddress;
    private ImageView mUserProfileImage;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private Button mConnectGoogle;
    private boolean isGoogleConnected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        isGoogleConnected = false;
        mUserProfileImage = findViewById(R.id.user_profile_image);
        mUserName = findViewById(R.id.edt_user_name);
        mUserEmailAddress = findViewById(R.id.edt_email_address);
        mUserMobileNumber = findViewById(R.id.edt_mobile_number);
        mUserBio = findViewById(R.id.edt_user_desc);
        mConnectGoogle = findViewById(R.id.google_connect_btn);

        loadUserInformation();

    }
    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            finish();
        }
        mCurrentUser = mAuth.getCurrentUser();
    }

    public void goBack(View pView){
        finish();
    }

    public void updateUserProfile(View pView){
        String name = String.valueOf(mUserName.getText());
        String email = String.valueOf(mUserName.getText());
        String bio = String.valueOf(mUserName.getText());
        String mobile = String.valueOf(mUserName.getText());
    }
    public  void findAuthProvider(){
        if(mCurrentUser!=null) {
            for (UserInfo user : mCurrentUser.getProviderData()) {
                if (user.getProviderId().equals("google.com")) {
                    isGoogleConnected = true;
                    mConnectGoogle.setText("Disconnect");
                    mConnectGoogle.setBackgroundColor(getResources().getColor(R.color.red_500));
                }
            }
        }
    }
    private void loadUserInformation() {
        findAuthProvider();
        if (mCurrentUser != null) {
            if (mCurrentUser.getPhotoUrl() != null) {
                String photoUrl = mCurrentUser.getPhotoUrl().toString();
                Glide.with(this)
                        .load(photoUrl)
                        .into(mUserProfileImage);
            }
            if (mCurrentUser.getDisplayName() != null) {
                String displayName = mCurrentUser.getDisplayName();
                mUserName.setText(displayName);
            }
            if(mCurrentUser.getEmail()!=null){
                String email = mCurrentUser.getEmail();
                mUserEmailAddress.setText(email);
            }
            if(mCurrentUser.getPhoneNumber()!=null){
                String mobile = mCurrentUser.getPhoneNumber();
                mUserMobileNumber.setText(mobile);
            }
        }
    }

}